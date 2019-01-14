package com.mitrais.study.bootcamp.controller.secure;

import com.google.common.base.Throwables;
import com.mitrais.study.bootcamp.controller.PersonController;
import com.mitrais.study.bootcamp.model.rs.Person;
import com.mitrais.study.bootcamp.service.FormAction;
import com.mitrais.study.bootcamp.service.PersonService;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.LockAcquisitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Rudi_W144
 */
@Controller
public class SecureController {

    private static final Logger log = LoggerFactory.getLogger(SecureController.class);

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

    @GetMapping(value = "/secure/person/add")
    public String showAddPage(final Model model,
                              @ModelAttribute(value = "person") Person upPerson) {
        model.addAttribute("action", FormAction.ADD.name());
        return "person-editor";
    }

    @GetMapping(value = "/secure/person/modify")
    public String showModifyPage(final Model model,
                                 @ModelAttribute(value = "person") Person upPerson) {
        model.addAttribute("action", FormAction.MODIFY.name());
        return "person-editor";
    }

    @PostMapping(value = "/secure/person/save")
    public String add(@ModelAttribute(value = "person") final Person upPerson,
                      final BindingResult result, final Model model, final HttpServletRequest request) {
        if (result.hasErrors()) {
            return "/secure/people/add";
        }
        try {
            personService.add(upPerson);

            return "redirect:/secure/people";
        } catch (Exception e) {
            log.error(String.format("Failed to signUp person: %s", e), e);
            final List<Throwable> causes = Throwables.getCausalChain(e);
            if (causes.stream().anyMatch((it) -> it instanceof LockAcquisitionException) || causes.stream().anyMatch((it) -> it instanceof ConstraintViolationException)) {
            } else {
            }
            return "/secure/people/add";
        }
    }

    @PutMapping(value = "/secure/person/save")
    public String modify(@ModelAttribute(value = "person") final Person upPerson,
                         final BindingResult result, final Model model, final HttpServletRequest request) {
        if (result.hasErrors()) {
            return "/secure/people/modify";
        }
        try {
            personService.add(upPerson);

            return "redirect:/secure/people";
        } catch (Exception e) {
            log.error(String.format("Failed to signUp person: %s", e), e);
            final List<Throwable> causes = Throwables.getCausalChain(e);
            if (causes.stream().anyMatch((it) -> it instanceof LockAcquisitionException) || causes.stream().anyMatch((it) -> it instanceof ConstraintViolationException)) {
            } else {
            }
            return "/secure/people/modify";
        }
    }
}
