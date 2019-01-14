package com.mitrais.study.bootcamp.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rudi_W144
 */
@RestController
public class WelcomeController {

    @RequestMapping("/")
    public String helloWorld(final Model model) {
        model.addAttribute("name", "Rudi Wijaya");
        return "Hello RUDI WIJAYA!!!";
    }
}
