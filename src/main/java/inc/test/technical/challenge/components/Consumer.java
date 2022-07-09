package inc.test.technical.challenge.components;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import inc.test.technical.challenge.models.KafkaPayment;
import inc.test.technical.challenge.models.Payment;
import inc.test.technical.challenge.services.PaymentService;

@Component
public class Consumer{

	@Autowired
	PaymentService paymentService;

	@KafkaListener(topics = {"online", "offline"}, groupId = "group", containerFactory = "paymentListener")
	public void consume(KafkaPayment kafkaPayment){
		try {
			paymentService.processPayment(new Payment(
						kafkaPayment.getPayment_id(),
						kafkaPayment.getAccount_id(),
						kafkaPayment.getPayment_type(),
						kafkaPayment.getCredit_card(),
						kafkaPayment.getAmount()
						));
			System.out.println("ReceivedData");
		} catch (IOException | InterruptedException e) {
		}
	}
}

