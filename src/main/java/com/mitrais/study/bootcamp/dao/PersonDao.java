package com.mitrais.study.bootcamp.dao;

import com.mitrais.study.bootcamp.data.MyPagingAndSortingRepository;
import com.mitrais.study.bootcamp.model.jpa.Person;

import javax.annotation.Nullable;

/**
 * @author Rudi_W144
 */
public interface PersonDao extends MyPagingAndSortingRepository<Person, Long> {

    /**
     *
     * @param username can be username or email
     * @return
     */
    @Nullable
    Person findOneByUsername(final String username);

    boolean deleteByUsername(final String username);

    @Nullable String getUsername(final long id);
}
