package inc.test.technical.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import inc.test.technical.challenge.models.PaymentError;
import io.netty.handler.timeout.ReadTimeoutException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class LogService{

	@Autowired
	WebClient.Builder webClientBuilder;

	/**
	* Sends an error to the logging microservice and hopes that it's accepted.
	* @param paymentError
	*/
	public void logError(PaymentError paymentError) {
		try{
			webClientBuilder.build()
				.post()
				.uri("/log")
				.body(Mono.just(paymentError), PaymentError.class)
				.retrieve()
				.toEntity(Void.class)
				.retryWhen(Retry.indefinitely()
						.filter(ex -> ex instanceof WebClientResponseException.ServiceUnavailable))
				.block();
		}
		catch(ReadTimeoutException runTimeException){
			System.out.println(runTimeException.getMessage());
		}

	}
}
