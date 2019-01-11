package com.mitrais.study.bootcamp.dao;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mitrais.study.bootcamp.data.MyPagingAndSortingRepository;
import com.mitrais.study.bootcamp.model.jpa.Role;

/**
 * @author Rudi_W144
 */
public interface RoleDao extends MyPagingAndSortingRepository<Role, Long> {

    ImmutableMap<Long, String> findNames(ImmutableList<Long> ids);
}
