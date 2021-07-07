package com.example.demo.persistence;

import com.example.demo.persistence.repositories.CategoryRepository;
import com.example.demo.persistence.repositories.QuestionRepository;
import org.hibernate.Session;

import javax.persistence.EntityManager;

public class UnitOfWork implements AutoCloseable {
    private boolean completed = false;

    private final EntityManager entityManager;

    // static reference to entityManagerFactory
    public static UnitOfWork createUnitOfWork(Session session) {
        var entityManagerFactory = session.entityManagerFactory;
        var entityManager = entityManagerFactory.createEntityManager();
        return new UnitOfWork(entityManager);
    }

    private UnitOfWork(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void begin() {
        entityManager.getTransaction().begin();
    }

    public void rollback() {
        // for more robust support, this should be wrapped in a try/catch/finally block
        entityManager.getTransaction().rollback();
        entityManager.close();
        completed = true;
    }

    public void commit() {
        // for more robust support, this should be wrapped in a try/catch/finally block
        entityManager.getTransaction().commit();
        entityManager.close();
        completed = true;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public CategoryRepository getCategoryRepository() {
        return new CategoryRepository(this);
    }
    public QuestionRepository getQuestionRepository() {
        return new QuestionRepository(this);
    }

    @Override
    public void close() {
        if (completed) return;
        rollback();
    }
}
