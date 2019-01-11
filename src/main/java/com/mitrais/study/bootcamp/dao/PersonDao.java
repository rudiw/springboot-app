package com.mitrais.study.bootcamp.dao;

import com.mitrais.study.bootcamp.data.MyPagingAndSortingRepository;
import com.mitrais.study.bootcamp.model.jpa;

public interface PersonDao extends MyPagingAndSortingRepository<jpa.Person, Integer> {

}
