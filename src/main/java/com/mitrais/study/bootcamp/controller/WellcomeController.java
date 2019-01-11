package com.mitrais.study.bootcamp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rudi_W144
 */
@RestController
public class WellcomeController {

    @RequestMapping("/")
    public String helloWorld() {
        return "Hello RUDI WIJAYA!!!";
    }
}
