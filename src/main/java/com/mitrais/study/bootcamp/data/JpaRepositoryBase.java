package com.mitrais.study.bootcamp.data;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
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

    @Override @Transactional(readOnly = true)
    public Iterable<T> findAll(Sort sort) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        // SELECT * FROM entityClass WHERE ... ORDER BY ... LIMIT x, y
        // FROM
        final CriteriaQuery<T> cq = cb.createQuery(entityClass);
        final Root<T> root = cq.distinct(true).from(entityClass);
        cq.select(root);
        // WHERE
        // ORDER BY
        final List<javax.persistence.criteria.Order> jpaOrders = FluentIterable
                .from(com.google.common.base.Optional.<Iterable<Sort.Order>>fromNullable(sort).or(ImmutableList.<Sort.Order>of()))
                .transform(new Function<Sort.Order, Order>() {
                    @Override
                    @Nullable
                    public javax.persistence.criteria.Order apply(@Nullable Sort.Order input) {
                        return input.getDirection() == Sort.Direction.ASC ? cb.asc(root.get(input.getProperty())) : cb.desc(root.get(input.getProperty()));
                    }
                }).toList();
        cq.orderBy(jpaOrders);

        final TypedQuery<T> typedQuery = em.createQuery(cq);
        final List<T> pageContent = typedQuery.getResultList();

        log.debug("findAll {} returned {} (sort by {})",
                entityClass.getSimpleName(), pageContent.size(), sort);
        return pageContent;
    }

    @Override @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        // SELECT COUNT(*) FROM entityClass WHERE ...
        final CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        final Root<T> countRoot = countCq.distinct(true).from(entityClass);
        countCq.select(cb.countDistinct(countRoot));
        final long totalRowCount = em.createQuery(countCq).getSingleResult();


        // SELECT * FROM entityClass WHERE ... ORDER BY ... LIMIT x, y
        // FROM
        final CriteriaQuery<T> cq = cb.createQuery(entityClass);
        final Root<T> root = cq.distinct(true).from(entityClass);
        cq.select(root);
        // WHERE
        // ORDER BY
        final List<javax.persistence.criteria.Order> jpaOrders = FluentIterable
                .from(com.google.common.base.Optional.<Iterable<Sort.Order>>fromNullable(pageable.getSort()).or(ImmutableList.<Sort.Order>of()))
                .transform(new Function<Sort.Order, Order>() {
                    @Override
                    @Nullable
                    public javax.persistence.criteria.Order apply(@Nullable Sort.Order input) {
                        return input.getDirection() == Sort.Direction.ASC ? cb.asc(root.get(input.getProperty())) : cb.desc(root.get(input.getProperty()));
                    }
                }).toList();
        cq.orderBy(jpaOrders);

        final TypedQuery<T> typedQuery = em.createQuery(cq)
                .setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize());
        final List<T> pageContent = typedQuery.getResultList();

        log.debug("findAll {} returned {} of {} rows (paged by {})",
                entityClass.getSimpleName(), pageContent.size(), totalRowCount, pageable);
        return new PageImpl<>(pageContent, pageable, totalRowCount);
    }

    @Override @Transactional(readOnly = false)
    public <S extends T> Iterable<S> saveAll(Iterable<S> iterable) {
        log.debug("Adding {} {} entities", ((Collection)iterable).size(), entityClass.getSimpleName());
        final List<S> savedItems = FluentIterable.from(iterable).transform(new Function<S, S>() {
            @Override @Nullable
            public S apply(@Nullable S input) {
                em.persist(input);
                return input;
            }
        }).toList();
        log.debug("Added {} {} entities", entities.size(), entityClass.getSimpleName());
        return savedItems;
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
