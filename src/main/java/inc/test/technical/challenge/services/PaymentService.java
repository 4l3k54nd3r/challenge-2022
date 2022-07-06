package inc.test.technical.challenge.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.test.technical.challenge.models.Payment;
import inc.test.technical.challenge.repos.PaymentRepository;

@Service
public class PaymentService{

	@Autowired
	PaymentRepository paymentRepository;

	public Boolean isValid(Payment payment) throws IOException, InterruptedException {
		ObjectMapper objectMapper = new ObjectMapper();
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			.method("POST", HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(payment)))
			.uri(URI.create("http://localhost:9000/payment"))
			.header("Accept", "application/json")
			.header("content-type", "application/json")
			.build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(objectMapper.writeValueAsString(payment));

		return response.statusCode() == 200 ? true : false;
	}

	public void processPayment(Payment payment) throws IOException, InterruptedException{
		System.out.println(payment.getPayment_id());
		System.out.println(payment.getAccount_id());
		System.out.println(payment.getPayment_type());
		System.out.println(payment.getCredit_card());
		System.out.println(payment.getAmount());
		if(isValid(payment)){
			paymentRepository.save(payment);
			System.out.println("Success");
		}
		else{
			System.out.println("Error");
		}

	}

}
