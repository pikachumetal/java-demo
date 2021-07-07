package com.example.demo.persistence.repositories;

import com.example.demo.domain.Topic;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.topic.TopicNonExistsProblem;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public class TopicRepository extends BaseRepository<Topic, String> {

    public TopicRepository(UnitOfWork unitOfWork) {
        super(Topic.class, unitOfWork);
    }

    @Modifying
    public Optional<Topic> toggle(String id) {
        var item = findById(id).orElseThrow();
        item.active = !item.active;
        return Optional.of(item);
    }

    @Modifying
    public Optional<Topic> update(String id, Topic item) {
        var oldItem = findById(id).orElseThrow();

        oldItem.description = item.description;
        oldItem.email = item.email;

        return Optional.of(oldItem);
    }

    public Optional<Topic> findByEmail(String email) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(repositoryClass);

        var author = criteriaQuery.from(repositoryClass);

        var idPredicate = criteriaBuilder.equal(author.get("email"), email);
        criteriaQuery.where(idPredicate);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getSingleOrDefault();
    }

    public Optional<Topic> findByDescription(String description) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(repositoryClass);

        var author = criteriaQuery.from(repositoryClass);

        var idPredicate = criteriaBuilder.equal(author.get("description"), description);
        criteriaQuery.where(idPredicate);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getSingleOrDefault();
    }

    @Override
    public void delete(String id) {
        var item = findById(id).orElseThrow();

        item.questions.forEach(this.entityManager::remove);
        this.entityManager.remove(item);
        
        entityManager.flush();
    }
}
