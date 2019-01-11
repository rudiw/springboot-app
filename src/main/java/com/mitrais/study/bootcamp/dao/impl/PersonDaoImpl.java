package com.mitrais.study.bootcamp.dao.impl;

import com.mitrais.study.bootcamp.data.JpaRepositoryBase;
import com.mitrais.study.bootcamp.dao.PersonDao;
import com.mitrais.study.bootcamp.model.jpa;

public class PersonDaoImpl extends JpaRepositoryBase<jpa.Person, Integer> implements PersonDao {

    public PersonDaoImpl() {
        super(jpa.Person.class);
    }


}
