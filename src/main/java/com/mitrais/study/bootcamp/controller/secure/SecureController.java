package com.mitrais.study.bootcamp.controller.secure;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rudi_W144
 */
@RestController
@RequestMapping(value = "/secure", name = "Secure Controller")
public class SecureController {

    @RequestMapping(value = "/welcome", name = "In secure controller, I welcome you")
    public String welcome() {
        return "Welcome in Secure Controller!";
    }




}
