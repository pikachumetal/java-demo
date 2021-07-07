package com.example.demo.persistence.repositories;

import com.example.demo.domain.Topic;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.topic.TopicNonExistsProblem;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public class TopicRepository extends BaseRepository<Topic,String>{

    public TopicRepository(UnitOfWork unitOfWork) {
        super(Topic.class, unitOfWork);
    }

    @Modifying
    public Optional<Topic> toggle(String id){
        var topic = findById(id).orElseThrow();
        topic.active = !topic.active;
        return Optional.of(topic);
    }

    @Modifying
    public Optional<Topic> update(String id, Topic topic){
        var oldTopic = findById(id).orElseThrow();

        oldTopic.description = topic.description;
        oldTopic.email = topic.email;

        return Optional.of(oldTopic);
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
}
