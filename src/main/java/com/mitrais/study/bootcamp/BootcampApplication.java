package com.mitrais.study.bootcamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BootcampApplication {

	public static final Logger log = LoggerFactory.getLogger(BootcampApplication.class);

	public static void main(String[] args) {
		log.info("Starting application...");
		final ConfigurableApplicationContext context = SpringApplication.run(BootcampApplication.class, args);
		log.info("Application is ready...");

		log.debug("holaaa");
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

