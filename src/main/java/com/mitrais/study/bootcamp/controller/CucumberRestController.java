package com.mitrais.study.bootcamp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/cucumber")
public class CucumberRestController {

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String hello() {
        return "Hello Cucumber...";
    }

}
