package inc.test.technical.challenge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("challenge.restapi")
public class RestProperties {
	private String baseUrl = "http://localhost:9000";

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}

