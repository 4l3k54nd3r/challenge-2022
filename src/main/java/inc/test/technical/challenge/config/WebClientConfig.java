package inc.test.technical.challenge.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;


/**
 * WebClient configuration
*/
@Configuration
@EnableConfigurationProperties(RestProperties.class)
public class WebClientConfig{

	@Autowired
	RestProperties restProperties;

	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder()
			.codecs(configurer -> {
				configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(new ObjectMapper(), MediaType.APPLICATION_JSON));
				configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(new ObjectMapper(), MediaType.APPLICATION_JSON));
			})
			.baseUrl(restProperties.getBaseUrl())
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
	}

	@Bean
	public WebClient.Builder webClient(){
		return webClientBuilder();
	}

}
