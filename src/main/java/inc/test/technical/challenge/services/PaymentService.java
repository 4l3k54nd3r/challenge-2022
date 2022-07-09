package inc.test.technical.challenge.services;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import inc.test.technical.challenge.models.Account;
import inc.test.technical.challenge.models.Payment;
import inc.test.technical.challenge.models.Payment.PaymentType;
import inc.test.technical.challenge.models.PaymentError;
import inc.test.technical.challenge.models.PaymentError.ErrorType;
import inc.test.technical.challenge.repos.AccountRepository;
import inc.test.technical.challenge.repos.PaymentRepository;
import reactor.core.publisher.Mono;

@Service
public class PaymentService{

	@Autowired
	WebClient webClient;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired AccountRepository accountRepository;

	@Autowired
	LogService logService;

	/**
	* Checks if the payment is valid
	* @param payment Payment object
	* @return Boolean deciding whether or not the payment is valid
	*/
	public Boolean isValid(Payment payment) {
		Boolean valid = false;
		try{
			ResponseEntity<Void> response = webClient
				.post()
				.uri("/payment")
				.body(Mono.just(payment), Payment.class)
				.retrieve()
				.toEntity(Void.class)
				.timeout(Duration.ofSeconds(1))
				.onErrorMap(TimeoutException.class, ex -> {
					logService.logError(new PaymentError(payment.getPayment_id(), ErrorType.NETWORK ,"Gateway timed out"));
					return new RuntimeException("Gateway timeout");
				})
				.block();
			if(response.getStatusCodeValue() == 200){
				valid = true;
			}
		}catch(RuntimeException runtimeException){}

		return valid;
	}

	/**
	* Writes to database if payment account exists
	* @param payment Payment object
	*/
	private void writeToDatabase(Payment payment) {
		Optional<Account> accountOptional = accountRepository.findByAccountId(payment.getAccount_id());
		//Check if account exists
		if(accountOptional.isPresent()){
			payment.setCreated_on(new Date(System.currentTimeMillis()));
			paymentRepository.save(payment);
			Account account = accountOptional.get();
			account.setLast_payment_date(new Date(System.currentTimeMillis()));
			accountRepository.save(account);
		}
		else{
			logService.logError(new PaymentError(payment.getPayment_id(), PaymentError.ErrorType.DATABASE, "Payment doesn't match an account"));
		}
	}

	/**
	 * Processes the payment
	 * @param payment Payment object
	 */
	public void processPayment(Payment payment) throws IOException, InterruptedException {
		//Offline payment
		if(payment.getPayment_type().equals(PaymentType.offline)){
			writeToDatabase(payment);
		}
		//Online payment
		else if(isValid(payment)){
			writeToDatabase(payment);
		}
	}

}
