package com.mitrais.study.bootcamp.data;

import com.google.common.collect.ImmutableList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Rudi_W144
 * @param <T>
 * @param <ID>
 */
public abstract class PagingAndSortingRepositoryImpl<T, ID> implements PagingAndSortingRepository<T, ID> {

    @Override @Transactional(readOnly = true)
    public abstract Iterable<T> findAll(Sort sort);

    @Override @Transactional(readOnly = true)
    public abstract Page<T> findAll(Pageable pageable);

    @Override @Transactional(readOnly = false)
    public <S extends T> S save(S s) {
        return this.saveAll(ImmutableList.of(s)).iterator().next();
    }

    @Override @Transactional(readOnly = false)
    public abstract <S extends T> Iterable<S> saveAll(Iterable<S> iterable);

    @Override @Transactional(readOnly = true)
    public abstract Optional<T> findById(ID id);

    @Override @Transactional(readOnly = true)
    public abstract boolean existsById(ID id);

    @Override @Transactional(readOnly = true)
    public abstract Iterable<T> findAll();

    @Override @Transactional(readOnly = true)
    public abstract Iterable<T> findAllById(Iterable<ID> iterable);

    @Override @Transactional(readOnly = true)
    public abstract long count();

    @Override @Transactional(readOnly = false)
    public abstract void deleteById(ID id);

    @Override @Transactional(readOnly = false)
    public void delete(T t) {
        this.deleteAll(ImmutableList.of(t));
    }

    @Override @Transactional(readOnly = false)
    public abstract void deleteAll(Iterable<? extends T> iterable);

    @Override @Transactional(readOnly = false)
    public abstract void deleteAll();
}
