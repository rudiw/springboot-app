package com.mitrais.study.bootcamp.service;

import com.mitrais.study.bootcamp.model.rs.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nullable;

public interface PersonService {

    Page<Person> findAll(final Pageable pageable);

    @Nullable
    Person findOne(final String username);

    Person add(final Person upPerson);

    Person modify(final String username, final Person upPerson);

    boolean remove(final String username);

    @Nullable
    String getUsername(final long id);

}
