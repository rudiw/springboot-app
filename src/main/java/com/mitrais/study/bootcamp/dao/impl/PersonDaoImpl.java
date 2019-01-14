package com.mitrais.study.bootcamp.dao.impl;

import com.mitrais.study.bootcamp.data.jpa.JpaRepositoryBase;
import com.mitrais.study.bootcamp.dao.PersonDao;
import com.mitrais.study.bootcamp.model.jpa.Person;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class PersonDaoImpl extends JpaRepositoryBase<Person, Long> implements PersonDao {

    public PersonDaoImpl() {
        super(Person.class);
    }


    @Nullable
    @Override
    @Transactional(readOnly = true)
    public Person findOneByUsername(final String username) {
        final TypedQuery<Person> tq = em.createQuery("SELECT p " +
                "FROM " + Person.class.getName() + " p " +
                "WHERE p.username = :upUsername", Person.class);
        tq.setParameter("upUsername", username);

        try {
            return tq.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override @Transactional(readOnly = false)
    public boolean deleteByUsername(final String username) {
        final Query query = em.createQuery("DELETE FROM " + Person.class.getName() + " p " +
               "WHERE p.username = :upUsername ");
        query.setParameter("upUsername", username);

        return query.executeUpdate() == 1;
    }
}
