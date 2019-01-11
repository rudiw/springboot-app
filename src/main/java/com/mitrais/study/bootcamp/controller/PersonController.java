package com.mitrais.study.bootcamp.controller;

import com.google.common.base.Throwables;
import com.mitrais.study.bootcamp.config.rs.PersonResource;
import com.mitrais.study.bootcamp.dao.PersonDao;
import com.mitrais.study.bootcamp.model.rs.Person;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.LockAcquisitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Rudi_W144
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @Inject
    private PersonDao personDao;
    @Inject
    private PersonAssembler personAssembler;
    @Autowired
    private PagedResourcesAssembler<Person> personPagedResourcesAssembler;
    @Inject
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String helloName(@PathVariable(value = "name", required = true) final String upName) {
        return "Hello " + upName;
    }

    @RequestMapping(value = "/username/{username}", consumes = "application/json", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Resource<Person>> findOne(@PathVariable(value = "username", required = true) final String upUsername) {
        final com.mitrais.study.bootcamp.model.jpa.Person jpaPerson = personDao.findOneByUsername(upUsername);
        if (jpaPerson == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Person person = new Person().copy(jpaPerson);
        final PersonResource res = personAssembler.toResource(person);
        return new ResponseEntity<>(res, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/all", consumes = "application/json", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<PagedResources> findAll(@PageableDefault(size = 10) final Pageable pageable) {
        final Page<com.mitrais.study.bootcamp.model.jpa.Person> page = personDao.findAll(pageable);
        final List<Person> people = page.getContent().stream().map(new Function<com.mitrais.study.bootcamp.model.jpa.Person, Person>() {
            @Override
            public Person apply(com.mitrais.study.bootcamp.model.jpa.Person jpaPerson) {
                return new Person().copy(jpaPerson);
            }
        }).collect(Collectors.toList());

        final PagedResources<Resource<Person>> resources =
                personPagedResourcesAssembler.toResource(new PageImpl<>(people, pageable, page.getTotalElements()));
        return new ResponseEntity<PagedResources>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<PersonResource> add(@RequestBody(required = true) Person upPerson) {
        try {
            final com.mitrais.study.bootcamp.model.jpa.Person jpaPerson =
                    new com.mitrais.study.bootcamp.model.jpa.Person().copy(upPerson);
            jpaPerson.setPassword(passwordEncoder.encode(jpaPerson.getPassword()));
            final com.mitrais.study.bootcamp.model.jpa.Person jpaAdd = personDao.add(jpaPerson);

            final Person person = new Person().copy(jpaAdd);
            final PersonResource res = personAssembler.toResource(person);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(String.format("Failed to add person: %s", e), e);
            final List<Throwable> causes = Throwables.getCausalChain(e);
            if (causes.stream().anyMatch((it) -> it instanceof LockAcquisitionException) || causes.stream().anyMatch((it) -> it instanceof ConstraintViolationException)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


}
