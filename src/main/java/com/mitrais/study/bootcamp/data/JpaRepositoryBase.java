package com.mitrais.study.bootcamp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * @author Rudi_W144
 * @param <T>
 * @param <ID>
 */
public abstract class JpaRepositoryBase<T, ID> extends PagingAndSortingRepositoryImpl<T, ID> {

    private final Class<T> entityClass;
    protected final EntityManager em;
    protected final Logger log;

    public JpaRepositoryBase(final Class<T> entityClass, final EntityManager em) {
        super();
        this.entityClass = entityClass;
        this.em = em;
        log = LoggerFactory.getLogger(JpaRepositoryBase.class.getName() + "/" + this.entityClass.getSimpleName());
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(ID id) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(ID id) {

    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
