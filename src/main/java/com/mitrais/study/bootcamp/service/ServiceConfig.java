package com.mitrais.study.bootcamp.service;

import com.mitrais.study.bootcamp.dao.PersonDao;
import com.mitrais.study.bootcamp.service.impl.PersonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;

@Configuration
public class ServiceConfig {

    @Inject
    private PlatformTransactionManager ptMgr;
    @Inject
    private PersonDao personDao;
    @Inject
    private PasswordEncoder passwordEncoder;

    @Bean
    public PersonService personService() {
        return new PersonServiceImpl(ptMgr, personDao, passwordEncoder);
    }

}
