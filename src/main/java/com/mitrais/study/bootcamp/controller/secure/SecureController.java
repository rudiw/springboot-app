package com.mitrais.study.bootcamp.controller.secure;

import com.mitrais.study.bootcamp.model.rs.Person;
import com.mitrais.study.bootcamp.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

/**
 * @author Rudi_W144
 */
@Controller
public class SecureController {

    @Inject
    private PersonService personService;

    @GetMapping(value = "/secure")
    public String secure() {
        return "secure";
    }

    @GetMapping(value = "/secure/people")
    public String showPeople(@PageableDefault(size = 20) final Pageable pageable,
                             final Model model) {
        final Page<Person> page = personService.findAll(pageable);
        model.addAttribute("people", page.getContent());

        return "people";
    }
}
