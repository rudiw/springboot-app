package com.mitrais.study.bootcamp.dao;

import com.mitrais.study.bootcamp.data.MyPagingAndSortingRepository;
import com.mitrais.study.bootcamp.model.jpa.Person;

public interface PersonDao extends MyPagingAndSortingRepository<Person, Integer> {

}
