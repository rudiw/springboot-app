package com.mitrais.study.bootcamp.controller;

import com.google.common.base.Throwables;
import com.mitrais.study.bootcamp.config.rs.PersonAssembler;
import com.mitrais.study.bootcamp.config.rs.PersonResource;
import com.mitrais.study.bootcamp.model.rs.Person;
import com.mitrais.study.bootcamp.service.PersonService;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.LockAcquisitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Rudi_W144
 */
@RestController
@RequestMapping("/rest/person")
public class PersonRestController {

    private static final Logger log = LoggerFactory.getLogger(PersonRestController.class);

    @Inject
    private PersonService personService;
    @Inject
    private PersonAssembler personAssembler;
    @Autowired
    private PagedResourcesAssembler<Person> personPagedResourcesAssembler;


    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String helloName(@PathVariable(value = "name", required = true) final String upName) {
        return "Hello " + upName;
    }

    @RequestMapping(value = "/username/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Resource<Person>> findOne(@PathVariable(value = "username", required = true) final String upUsername) {
        final Person person = personService.findOne(upUsername);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final PersonResource res = personAssembler.toResource(person);
        return new ResponseEntity<>(res, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<PagedResources> findAll(@PageableDefault(size = 10) final Pageable pageable) {
        final Page<Person> page = personService.findAll(pageable);

        final PagedResources<PersonResource> resources = personPagedResourcesAssembler.toResource(page, personAssembler);
        return new ResponseEntity<PagedResources>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<PersonResource> add(@RequestBody(required = true) Person upPerson) {
        try {
            final Person person = personService.add(upPerson);
            final PersonResource res = personAssembler.toResource(person);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(String.format("Failed to signUp person: %s", e), e);
            final List<Throwable> causes = Throwables.getCausalChain(e);
            if (causes.stream().anyMatch((it) -> it instanceof LockAcquisitionException) || causes.stream().anyMatch((it) -> it instanceof ConstraintViolationException)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @RequestMapping(value = "/modify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<PersonResource> modify(@RequestBody(required = true) Person upPerson) {
        try {
            final Person person = personService.modify(upPerson.getUsername(), upPerson);

            final PersonResource res = personAssembler.toResource(person);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(String.format("Failed to signUp person: %s", e), e);
            final List<Throwable> causes = Throwables.getCausalChain(e);
            if (causes.stream().anyMatch((it) -> it instanceof LockAcquisitionException) || causes.stream().anyMatch((it) -> it instanceof ConstraintViolationException)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @RequestMapping(value = "/remove/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> remove(@PathVariable(value = "username", required = true) final String upUsername) {
        try {
            personService.remove(upUsername);

            return new ResponseEntity<>(HttpStatus.GONE);
        } catch (Exception e) {
            log.error(String.format("Failed to signUp person: %s", e), e);
            final List<Throwable> causes = Throwables.getCausalChain(e);
            if (causes.stream().anyMatch((it) -> it instanceof LockAcquisitionException) || causes.stream().anyMatch((it) -> it instanceof ConstraintViolationException)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


}
