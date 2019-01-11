package com.mitrais.study.bootcamp.dao.impl;

import com.mitrais.study.bootcamp.data.jpa.JpaRepositoryBase;
import com.mitrais.study.bootcamp.dao.PersonDao;
import com.mitrais.study.bootcamp.model.jpa.Person;

public class PersonDaoImpl extends JpaRepositoryBase<Person, Integer> implements PersonDao {

    public PersonDaoImpl() {
        super(Person.class);
    }


}
