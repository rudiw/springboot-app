package com.mitrais.study.bootcamp;

import com.mitrais.study.bootcamp.config.db.LiquibaseConfig;
import com.mitrais.study.bootcamp.service.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({LiquibaseConfig.class, ServiceConfig.class})
public class BootcampApplication {

	public static final Logger log = LoggerFactory.getLogger(BootcampApplication.class);

	public static void main(String[] args) {
		log.info("Starting application...");
		final ConfigurableApplicationContext context = SpringApplication.run(BootcampApplication.class, args);
		log.info("Application is ready...");
	}

}

