package com.example.consumingrest;

import com.example.consumingrest.JsonToXML.JsonToXML;
import com.example.consumingrest.xpath.Xpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumingRestApplication {
	private static final Logger log = LoggerFactory.getLogger(ConsumingRestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ConsumingRestApplication.class, args);
		Xpath.displayTipoDni();
		Xpath.displayDni();
		JsonToXML.JsonToXML();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	/*
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			ResponseEntity<Account[]> response =
					restTemplate.getForEntity(
							"https://homebanking-citi-backend.herokuapp.com/api/account",
							Account[].class);
			Account[] accounts = response.getBody();
			log.info(Arrays.toString(accounts));
		};
	}
	 */
}
