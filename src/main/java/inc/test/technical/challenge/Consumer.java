package inc.test.technical.challenge;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import inc.test.technical.challenge.models.Payment;

@Component
public class Consumer{

	@KafkaListener(topics = "online", groupId = "group", containerFactory = "paymentListener")

	public void consume(Payment payment){
		System.out.println(payment.getCredit_card() + "\n");
	}
}
