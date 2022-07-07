package inc.test.technical.challenge.services;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriBuilder;

import inc.test.technical.challenge.models.Account;
import inc.test.technical.challenge.models.Payment;
import inc.test.technical.challenge.models.PaymentError;
import inc.test.technical.challenge.repos.PaymentRepository;
import io.netty.handler.timeout.ReadTimeoutException;
import reactor.core.publisher.Mono;
import inc.test.technical.challenge.repos.AccountRepository;

@Service
public class PaymentService{

	//@Autowired
	//WebClient.Builder webClientBuilder;

	@Autowired
	WebClient webClient;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired AccountRepository AccountRepository;

	@Autowired
	LogService logService;

	public Boolean isValid(Payment payment) {
		//WebClient webClient = webClientBuilder.build();
		Boolean valid = false;
		try{
			ResponseEntity<Void> response = webClient
				.post()
				.uri("/payment")
				//.body(Mono.just(new AltPayment(payment.getPayment_id(), payment.getAccount_id(), payment.getPayment_type(),payment.getCredit_card(), payment.getAmount())), AltPayment.class)
				//.body(Mono.just(new AltPayment(payment)), AltPayment.class)
				.body(Mono.just(payment), Payment.class)
				.retrieve()
				.toEntity(Void.class)
				.timeout(Duration.ofSeconds(1))
				//.onErrorMap(ReadTimeoutException.class, ex -> new HttpTimeoutException("Gateway timed out"))
				.onErrorMap(TimeoutException.class, ex -> {
					System.out.println("Timed out");
					logService.logError(new PaymentError(payment.getPayment_id(), "504","Gateway timed out" ));
					return new RuntimeException("Gateway timeout");
				})
				.block();
			System.out.println(response.getStatusCodeValue());
			if(response.getStatusCodeValue() == 200){
				valid = true;
			}
		}catch(RuntimeException runtimeException){}

			//.bodyToMono(ClientResponse.class);
		//if(result.block().statusCode().value() == 200){
		//	valid = true;
		//}

		/*
		 *WebClient.ResponseSpec response = webClient.get().uri("/payments").retrieve();
		 *ObjectMapper objectMapper = new ObjectMapper();
		 *HttpClient httpClient = HttpClient.newHttpClient();
		 *HttpRequest request = HttpRequest.newBuilder()
		 *    .method("POST", HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(payment)))
		 *    .uri(URI.create("http://localhost:9000/payment"))
		 *    .header("Accept", "application/json")
		 *    .header("content-type", "application/json")
		 *    .build();
		 *HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		 *System.out.println(objectMapper.writeValueAsString(payment));
		 */

		return valid; //50 == 200 ? true : false;
	}

	public void processPayment(Payment payment) throws IOException, InterruptedException{
		if(isValid(payment) || payment.getPayment_type().equals("OFFLINE")){
			Optional<Account> accountOptional = AccountRepository.findByAccountId(payment.getAccount_id());
			if(accountOptional.isPresent()){
				paymentRepository.save(payment);
				Account account = accountOptional.get();
				account.setLast_payment_date(new Date(System.currentTimeMillis()));
				AccountRepository.save(account);
				System.out.println("Success");
			}
			else{
				logService.logError(new PaymentError(payment.getPayment_id(), "Bad user", "Payment doesn't match an account"));
				System.out.println("No matching account");
			}
		}

	}

}
