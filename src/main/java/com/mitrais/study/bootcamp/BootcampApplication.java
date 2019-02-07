package com.mitrais.study.bootcamp;

import com.mitrais.study.bootcamp.data.jpa.JpaRepositoryBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BootcampApplication {

	public static final Logger log = LoggerFactory.getLogger(BootcampApplication.class);

	public static void main(String[] args) {
		log.info("Starting application...");
		final ConfigurableApplicationContext context = SpringApplication.run(BootcampApplication.class, args);
		log.info("Application is ready...");

		log.debug("holaaa");

	}

}

