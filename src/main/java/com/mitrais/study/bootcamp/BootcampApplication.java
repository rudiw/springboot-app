package com.mitrais.study.bootcamp;

import com.mitrais.study.bootcamp.dao.PersonDao;
import com.mitrais.study.bootcamp.model.jpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootApplication
public class BootcampApplication {

	public static final Logger log = LoggerFactory.getLogger(BootcampApplication.class);

	public static void main(String[] args) {
		log.info("Starting application...");
		final ConfigurableApplicationContext context = SpringApplication.run(BootcampApplication.class, args);

		log.info("Application is ready...");

		final PersonDao personDao = context.getBean("personDao", PersonDao.class);
		final Page<jpa.Person> page = personDao.findAll(PageRequest.of(0, 10));
		for (final jpa.Person person : page.getContent()) {
			log.info("Person: {}", person);
		}
	}

}

