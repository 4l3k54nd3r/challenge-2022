package inc.test.technical.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	* Sends an error to the logging microservice and hopes that it's accepted.
	* @param paymentError
	*/
	public void logError(PaymentError paymentError) {
		try{
			ResponseEntity<Void> response = webClient
				.post()
				.uri("/log")
				.body(Mono.just(paymentError), PaymentError.class)
				.retrieve()
				.toEntity(Void.class)
				.block();
		}
		catch(ReadTimeoutException runTimeException){
			System.out.println(runTimeException.getMessage());
		}

	}
}
