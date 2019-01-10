package com.mitrais.study.bootcamp.dao;

import com.mitrais.study.bootcamp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDao extends JpaRepository<Person, Integer> {

    Person findOne(int id);

}
