package com.example.dao;

import com.example.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class DaoRepository<K extends Serializable, E extends BaseEntity<K>> implements Dao<K, E> {

    private final Class<E> clazz;
    private final EntityManager entityManager;


    public E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void delete(K id) {
        entityManager.remove(entityManager.find(clazz, id));
        entityManager.flush();
    }

    public void update(E entity) {
        entityManager.merge(entity);
        entityManager.flush();
    }

    public Optional<E> findById(K id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    public List<E> findAll() {
        var criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        return entityManager.createQuery(criteria)
                .getResultList();
    }

}
