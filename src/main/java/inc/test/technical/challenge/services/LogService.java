package inc.test.technical.challenge.services;

import java.net.http.HttpTimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import inc.test.technical.challenge.models.PaymentError;
import io.netty.handler.timeout.ReadTimeoutException;
import reactor.core.publisher.Mono;

@Service
public class LogService{

	@Autowired
	WebClient webClient;

	public void logError(PaymentError paymentError){
		try{
			ResponseEntity<Void> response = webClient
				.post()
				.uri("/log")
				//.body(Mono.just(new AltPayment(payment.getPayment_id(), payment.getAccount_id(), payment.getPayment_type(),payment.getCredit_card(), payment.getAmount())), AltPayment.class)
				//.body(Mono.just(new AltPayment(payment)), AltPayment.class)
				.body(Mono.just(paymentError), PaymentError.class)
				.retrieve()
				.toEntity(Void.class)
				//.onErrorMap(ReadTimeoutException.class, ex -> new HttpTimeoutException("Gateway timed out"))
				//.onErrorMap(ReadTimeoutException.class, ex -> {
					//System.out.println("Timed out");
					//return new HttpTimeoutException("Timed out");
				//})
				.block();
			System.out.println("Logging error");
			System.out.println(response.getStatusCodeValue());

		}
		catch(ReadTimeoutException runTimeException){
			System.out.println(runTimeException.getMessage());
		}

	}
}
