package be.matthiasdepoorter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LyricalMiracleConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
