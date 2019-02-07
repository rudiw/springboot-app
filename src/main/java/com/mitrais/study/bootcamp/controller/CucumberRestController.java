package com.mitrais.study.bootcamp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/rest/cucumber")
public class CucumberRestController {

    private static final Logger log = LoggerFactory.getLogger(CucumberRestController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String hello() {
        log.info("Came in hello cucumber!");
        return "Hello Cucumber...";
    }

}
