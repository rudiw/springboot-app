package com.mitrais.study.bootcamp.config.rs;

import com.mitrais.study.bootcamp.model.rs.Person;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

/**
 * @author Rudi_W144
 */
public class PersonResource extends Resource<Person> {

    public PersonResource(final Person content, final Link[] links) {
        super(content, links);
    }
}
