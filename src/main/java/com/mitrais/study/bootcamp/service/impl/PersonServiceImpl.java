package com.mitrais.study.bootcamp.service.impl;

import com.mitrais.study.bootcamp.dao.PersonDao;
import com.mitrais.study.bootcamp.model.rs.Person;
import com.mitrais.study.bootcamp.service.PersonException;
import com.mitrais.study.bootcamp.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PersonServiceImpl implements PersonService {

    private PersonDao personDao;
    private PasswordEncoder passwordEncoder;
    private final TransactionTemplate txTemplate;

    public PersonServiceImpl(final PlatformTransactionManager ptMgr,
                             final PersonDao personDao, final PasswordEncoder passwordEncoder) {
        super();

        this.txTemplate = new TransactionTemplate(ptMgr);
        txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        this.personDao = personDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<Person> findAll(final Pageable pageable) {
        final Page<Person> execute = txTemplate.execute(new TransactionCallback<Page<Person>>() {
            @Override
            public Page<Person> doInTransaction(TransactionStatus status) {
                final Page<com.mitrais.study.bootcamp.model.jpa.Person> page = personDao.findAll(pageable);
                final List<Person> people = page.getContent().stream().map(new Function<com.mitrais.study.bootcamp.model.jpa.Person, Person>() {
                    @Override
                    public Person apply(com.mitrais.study.bootcamp.model.jpa.Person jpaPerson) {
                        return new Person().copy(jpaPerson);
                    }
                }).collect(Collectors.toList());

                return new PageImpl<>(people, pageable, page.getTotalElements());
            }
        });

        return execute;
    }

    @Override
    @Nullable public Person findOne(final String username) {
        final Person execute = txTemplate.execute(new TransactionCallback<Person>() {
            @Override
            public Person doInTransaction(TransactionStatus status) {
                @Nullable final com.mitrais.study.bootcamp.model.jpa.Person jpa = personDao.findOneByUsername(username);
                if (jpa == null) {
                    return null;
                }
                final Person person = new Person().copy(jpa);
                return person;
            }
        });
        return execute;
    }

    @Override
    public Person add(final Person upPerson) {
        final Person execute = txTemplate.execute(new TransactionCallback<Person>() {
            @Override
            public Person doInTransaction(TransactionStatus status) {
                final com.mitrais.study.bootcamp.model.jpa.Person jpaPerson =
                        new com.mitrais.study.bootcamp.model.jpa.Person().insert(upPerson);
                jpaPerson.setPassword(passwordEncoder.encode(jpaPerson.getPassword()));

                final com.mitrais.study.bootcamp.model.jpa.Person jpaAdd = personDao.add(jpaPerson);

                final Person person = new Person().copy(jpaAdd);
                return person;
            }
        });
        return execute;
    }

    @Override
    public Person modify(final String username, final Person upPerson) {
        final Person execute = txTemplate.execute(new TransactionCallback<Person>() {
            @Override
            public Person doInTransaction(TransactionStatus status) {
                @Nullable final com.mitrais.study.bootcamp.model.jpa.Person existing = personDao.findOneByUsername(username);
                if (existing == null) {
                    throw new PersonException(String.format("Person is not found!"));
                }

                existing.update(upPerson);
                existing.setPassword(passwordEncoder.encode(existing.getPassword()));

                final com.mitrais.study.bootcamp.model.jpa.Person jpaModify = personDao.modify(existing.getId(), existing);

                final Person person = new Person().copy(jpaModify);
                return person;
            }
        });
        return execute;
    }

    @Override
    public boolean remove(final String username) {
        final Boolean execute = txTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                return personDao.deleteByUsername(username);
            }
        });

        return execute;
    }

    @Nullable @Override
    public String getUsername(long id) {
        final String username = txTemplate.execute(new TransactionCallback<String>() {
            @Override
            public String doInTransaction(TransactionStatus status) {
                return personDao.getUsername(id);
            }
        });
        return username;
    }
}
