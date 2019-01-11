package com.mitrais.study.bootcamp.dao.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mitrais.study.bootcamp.dao.RoleDao;
import com.mitrais.study.bootcamp.data.jpa.JpaRepositoryBase;
import com.mitrais.study.bootcamp.model.jpa.Role;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Rudi_W144
 */
public class RoleDaoImpl extends JpaRepositoryBase<Role, Long> implements RoleDao {

    public RoleDaoImpl() {
        super(Role.class);
    }

    @Override @Transactional(readOnly = true)
    public ImmutableMap<Long, String> findNames(ImmutableList<Long> ids) {
        Preconditions.checkNotNull(ids, "Param Ids must not be null for finding names of role");
        Preconditions.checkState(!ids.isEmpty(), "Param Ids must not be empty for finding names of fole");

        final TypedQuery<Tuple> tq = em.createQuery("SELECT r.id AS id, r.name AS name " +
                "FROM " + Role.class.getName() + " r " +
                "WHERE r.id IN :upIds ", Tuple.class);
        tq.setParameter("upIds", ids);

        final List<Tuple> tuples = tq.getResultList();
        final Map<Long, String> map = tuples.stream().collect( Collectors.toMap(new Function<Tuple, Long>() {
            @Override
            public Long apply(Tuple tuple) {
                return Long.parseLong(String.valueOf(tuple.get("id")));
            }
        }, new Function<Tuple, String>() {
            @Override
            public String apply(Tuple tuple) {
                return String.valueOf(tuple.get("name"));
            }
        }) );

        return ImmutableMap.copyOf(map);
    }
}
