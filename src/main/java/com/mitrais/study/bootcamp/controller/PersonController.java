package com.mitrais.study.bootcamp.controller;

import com.google.common.base.Throwables;
import com.mitrais.study.bootcamp.config.rs.PersonAssembler;
import com.mitrais.study.bootcamp.model.rs.Person;
import com.mitrais.study.bootcamp.model.rs.UserInfo;
import com.mitrais.study.bootcamp.service.PersonService;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.LockAcquisitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Rudi_W144
 */
@Controller
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @Inject
    private PersonService personService;
    @Inject
    private PersonAssembler personAssembler;
    @Autowired
    private PagedResourcesAssembler<Person> personPagedResourcesAssembler;
    @Autowired
    private AuthenticationManager authMgr;

    @GetMapping(value = "/signup")
    public String showSignUpForm(@ModelAttribute(value = "person") Person upPerson) {
        return "sign-up";
    }

    @PostMapping(value = "/person/signUp")
    public String signUp(@ModelAttribute(value = "person") final Person upPerson,
                         final BindingResult result, final Model model, final HttpServletRequest request) {
        if (result.hasErrors()) {
            return "sign-up";
        }
        try {
            personService.add(upPerson);

            //make him/her auto login...
            final UsernamePasswordAuthenticationToken authToken = new
                    UsernamePasswordAuthenticationToken(upPerson.getUsername(), upPerson.getRawPassword());
            authToken.setDetails(new WebAuthenticationDetails(request));
            final Authentication authentication = authMgr.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/secure";
        } catch (Exception e) {
            log.error(String.format("Failed to signUp person: %s", e), e);
            final List<Throwable> causes = Throwables.getCausalChain(e);
            if (causes.stream().anyMatch((it) -> it instanceof LockAcquisitionException) || causes.stream().anyMatch((it) -> it instanceof ConstraintViolationException)) {
            } else {
            }
            return "sign-up";
        }
    }

    @GetMapping(value = "/signin")
    public String showSignInForm(@ModelAttribute(value = "user") UserInfo upUser) {
        return "sign-in";
    }

    @GetMapping(value = "person/signIn")
    public String signIn(@ModelAttribute(value = "user") final UserInfo user,
                         final HttpServletRequest request) {
        try {
            //make him/her auto login...
            final UsernamePasswordAuthenticationToken authToken = new
                    UsernamePasswordAuthenticationToken(user.getUsername(), user.getRawPassword());
            authToken.setDetails(new WebAuthenticationDetails(request));
            final Authentication authentication = authMgr.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/";
        } catch (Exception e) {
            log.error(String.format("Failed to signIn person: %s", e), e);
            final List<Throwable> causes = Throwables.getCausalChain(e);
            if (causes.stream().anyMatch((it) -> it instanceof LockAcquisitionException) || causes.stream().anyMatch((it) -> it instanceof ConstraintViolationException)) {
            } else {
            }
            return "sign-in";
        }
    }


}
