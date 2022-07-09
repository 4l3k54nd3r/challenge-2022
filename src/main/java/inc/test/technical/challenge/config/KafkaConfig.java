package inc.test.technical.challenge.config;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import inc.test.technical.challenge.models.KafkaPayment;

/**
 * Does Kafka things
 */
@EnableKafka
@Configuration
public class KafkaConfig {
	@Autowired
	ObjectMapper objectMapper;

	@Bean
	public ConsumerFactory<String, KafkaPayment> consumerFactory() {

		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				"127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG,
				"group");
		config.put(
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);
		config.put(
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				ObjectMapper.class);

		return new DefaultKafkaConsumerFactory<>(
				config,
				new StringDeserializer(),
				new JsonDeserializer<>(KafkaPayment.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, KafkaPayment> paymentListener() {
		ConcurrentKafkaListenerContainerFactory<String, KafkaPayment> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

}
