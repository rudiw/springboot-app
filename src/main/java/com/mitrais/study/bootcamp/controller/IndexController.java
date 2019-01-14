package com.mitrais.study.bootcamp.controller;

import com.google.common.base.Preconditions;
import com.mitrais.study.bootcamp.model.rs.UserInfo;
import com.mitrais.study.bootcamp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * @author Rudi_W144
 */
@Controller
public class IndexController {

    @Autowired
    private AuthenticationManager authMgr;
    @Inject
    private PersonService personService;

    @GetMapping(value = "/")
    public String index(final Model model, @Nullable  final Authentication auth) {
        final UserInfo user;
        if (auth != null && auth.isAuthenticated()) {
            final User principal = (User) auth.getPrincipal();
            final String username = Preconditions.checkNotNull(personService.getUsername(Long.parseLong(principal.getUsername())),
                    "Username must not be null for signed-in person '%s'", principal.getUsername());
            user = new UserInfo(auth.isAuthenticated(), username);
        } else {
            user = new UserInfo(false, "guest");
        }

        model.addAttribute("user", user);

        return "index";
    }
}
