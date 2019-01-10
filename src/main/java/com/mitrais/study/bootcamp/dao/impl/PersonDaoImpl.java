package com.mitrais.study.bootcamp.dao.impl;

import com.mitrais.study.bootcamp.data.JpaRepositoryBase;
import com.mitrais.study.bootcamp.dao.PersonDao;
import com.mitrais.study.bootcamp.model.Person;

import javax.persistence.EntityManager;

public class PersonDaoImpl extends JpaRepositoryBase<Person, Integer> implements PersonDao {

    public PersonDaoImpl() {
        super(Person.class);
    }


}
