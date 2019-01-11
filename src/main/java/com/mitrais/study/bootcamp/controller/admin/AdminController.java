package com.mitrais.study.bootcamp.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rudi_W144
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "Welcom Admin...";
    }
}
