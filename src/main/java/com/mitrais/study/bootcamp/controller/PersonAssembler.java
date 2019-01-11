package com.mitrais.study.bootcamp.controller;

import com.mitrais.study.bootcamp.config.rs.PersonResource;
import com.mitrais.study.bootcamp.model.rs.Person;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Rudi_W144
 */
@Component
public class PersonAssembler extends ResourceAssemblerSupport<Person, PersonResource> {

    public PersonAssembler() {
        super(PersonController.class, PersonResource.class);
    }

    @Override
    public PersonResource toResource(Person entity) {
        final PersonResource res = instantiateResource(entity);

        final Link link = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(PersonController.class).findOne(entity.getUsername()))
                .withRel("person");
        res.add(link);
        return res;
    }

    @Override
    protected PersonResource instantiateResource(Person entity) {
        return new PersonResource(entity, new Link[]{});
    }
}
