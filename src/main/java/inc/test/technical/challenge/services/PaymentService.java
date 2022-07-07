package inc.test.technical.challenge.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import inc.test.technical.challenge.models.Account;
import inc.test.technical.challenge.models.Payment;
import inc.test.technical.challenge.repos.PaymentRepository;
import reactor.core.publisher.Mono;
import inc.test.technical.challenge.repos.AccountRepository;

@Service
public class PaymentService{

	@Autowired
	WebClient.Builder webClientBuilder;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired AccountRepository AccountRepository;

	public Boolean isValid(Payment payment) throws IOException, InterruptedException {
		WebClient webClient = webClientBuilder.build();
		Mono<Payment> response = webClient
			.post()
			.uri("/payments")
			.body(BodyInserters.fromPublisher(Mono.just(payment), Payment.class))
			.retrieve()
			.bodyToMono(Payment.class);




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

		return true; //50 == 200 ? true : false;
	}

	private void logError(String paymentId){

	}

	public void processPayment(Payment payment) throws IOException, InterruptedException{
		if(isValid(payment)){
			Optional<Account> accountOptional = AccountRepository.findByAccountId(payment.getAccount_id());
			if(accountOptional.isPresent()){
				paymentRepository.save(payment);
				Account account = accountOptional.get();
				account.setLast_payment_date(new Date(System.currentTimeMillis()));
				AccountRepository.save(account);
			}
			System.out.println("Success");
		}
		else{
			System.out.println("Error");
		}

	}

}
