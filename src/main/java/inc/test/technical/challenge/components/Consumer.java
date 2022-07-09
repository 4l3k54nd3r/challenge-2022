package inc.test.technical.challenge.components;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import inc.test.technical.challenge.models.Payment;
import inc.test.technical.challenge.services.PaymentService;

@Component
public class Consumer{

	@Autowired
	PaymentService paymentService;

	@Autowired
	ObjectMapper objectMapper;

	@KafkaListener(topics = {"online", "offline"})
	public void consume(String paymentData) throws JsonMappingException, JsonProcessingException{
		Payment payment = objectMapper.readValue(paymentData, Payment.class);

		try {
			paymentService.processPayment(payment);
		}catch (IOException | InterruptedException e) {
		}
	}
}

