package com.mitrais.study.bootcamp.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rudi_W144
 */
@RestController
@RequestMapping(value = "/rest/admin")
public class AdminRestController {

    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "Welcom Admin...";
    }
}
