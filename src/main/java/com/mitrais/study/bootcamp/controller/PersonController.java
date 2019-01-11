package com.mitrais.study.bootcamp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rudi_W144
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    @RequestMapping(value = "/{name}")
    public String helloName(@PathVariable(value = "name", required = true) final String upName) {
        return "Hello " + upName;
    }

}
