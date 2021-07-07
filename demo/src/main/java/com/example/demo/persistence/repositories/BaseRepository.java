package com.example.demo.persistence.repositories;

import com.example.demo.domain.AggregateRoot;
import com.example.demo.persistence.UnitOfWork;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T extends AggregateRoot<ID>, ID> {
    protected final EntityManager entityManager;
    protected final Class<T> repositoryClass;

    public BaseRepository(Class<T> repositoryClass, UnitOfWork unitOfWork) {
        this.entityManager = unitOfWork.getEntityManager();
        this.repositoryClass = repositoryClass;
    }

    public List<T> findAll() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(repositoryClass);
        criteriaQuery.from(repositoryClass);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public Optional<T> findById(ID id) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(repositoryClass);

        var root = criteriaQuery.from(repositoryClass);

        var idPredicate = criteriaBuilder.equal(root.get("id"), id);
        criteriaQuery.where(idPredicate);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getSingleOrDefault();
    }

    public T save(T item) {
        if (item.isNew) this.entityManager.persist(item);
        else this.entityManager.merge(item);

        entityManager.flush();
        return findById(item.getId()).orElseThrow();
    }

    public void delete(ID id) {
        var item = findById(id).orElseThrow();
        entityManager.remove(item);
        entityManager.flush();
    }
}
