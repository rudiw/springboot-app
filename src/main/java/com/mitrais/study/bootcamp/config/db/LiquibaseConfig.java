package com.mitrais.study.bootcamp.config.db;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SpringLiquibase liquibase() {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/db/changelog/db.changelog-master.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
