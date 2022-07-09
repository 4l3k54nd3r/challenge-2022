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
import org.springframework.web.reactive.function.client.WebClientResponseException;

import inc.test.technical.challenge.models.Account;
import inc.test.technical.challenge.models.Payment;
import inc.test.technical.challenge.models.Payment.PaymentType;
import inc.test.technical.challenge.models.PaymentError;
import inc.test.technical.challenge.models.PaymentError.ErrorType;
import inc.test.technical.challenge.repos.AccountRepository;
import inc.test.technical.challenge.repos.PaymentRepository;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class PaymentService{

	@Autowired
	WebClient.Builder webClientBuilder;

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
			ResponseEntity<Void> response = webClientBuilder.build()
				.post()
				.uri("/payment")
				.body(Mono.just(payment), Payment.class)
				.retrieve()
				.toEntity(Void.class)
				.timeout(Duration.ofSeconds(1))
				.onErrorMap(TimeoutException.class, ex -> {
					logService.logError(new PaymentError(payment.getId(), ErrorType.NETWORK ,"Gateway timed out"));
					return new RuntimeException("Gateway timeout");
				})
				.retryWhen(Retry.indefinitely()
						.filter(ex -> ex instanceof WebClientResponseException.ServiceUnavailable))

				.block();
			if(response.getStatusCodeValue() == 200){
				valid = true;
			}
			else{
				System.out.println(response.getStatusCodeValue());
			}
		}catch(RuntimeException runtimeException){}

		return valid;

	}

	/**
	* Writes to database
	* @param payment Payment object
	* @param account Account associated with the payment
	*/
	private void writeToDatabase(Payment payment, Account account) {
		//Check if account exists
			//check if payment has already been processed
			payment.setCreatedAt(new Date(System.currentTimeMillis()));
			payment = paymentRepository.save(payment);
			account.setLastPaymentDate(payment.getCreatedAt());
			accountRepository.save(account);
	}

	/**
	 * Processes the payment
	 * @param payment Payment object
	 */
	public void processPayment(Payment payment) throws IOException, InterruptedException {
		Optional<Account> accountOptional = accountRepository.findByAccountId(payment.getAccountId());
		if(paymentRepository.existsById(payment.getId())){
			logService.logError(new PaymentError(payment.getId(), PaymentError.ErrorType.OTHER, "Payment already exists in database"));
		}
		else if(accountOptional.isEmpty()){
			logService.logError(new PaymentError(payment.getId(), PaymentError.ErrorType.DATABASE, "Payment doesn't match an account"));
		}
		//Offline payment
		else if(payment.getPaymentType().equals(PaymentType.offline)){
			writeToDatabase(payment, accountOptional.get());
		}
		//Online payment
		else if(isValid(payment)){
			writeToDatabase(payment, accountOptional.get());
		}
	}

}
