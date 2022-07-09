package inc.test.technical.challenge.components;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import inc.test.technical.challenge.models.Payment;
import inc.test.technical.challenge.services.PaymentService;

@Component
public class Consumer{

	@Autowired
	PaymentService paymentService;

	@KafkaListener(topics = {"online", "offline"}, groupId = "group", containerFactory = "paymentListener")
	public void consume(Payment payment){
		try {
			paymentService.processPayment(payment);
		} catch (IOException | InterruptedException e) {
		}
	}
}

