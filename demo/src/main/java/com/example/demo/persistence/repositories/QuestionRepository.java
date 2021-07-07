package com.example.demo.persistence.repositories;

import com.example.demo.domain.Question;
import com.example.demo.persistence.UnitOfWork;

import java.util.List;
import java.util.Optional;

public class QuestionRepository extends BaseRepository<Question, String> {
    public QuestionRepository(UnitOfWork unitOfWork) {
        super(Question.class, unitOfWork);
    }

    public Optional<Question> findByEmail(String email) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(repositoryClass);

        var author = criteriaQuery.from(repositoryClass);

        var idPredicate = criteriaBuilder.equal(author.get("email"), email);
        criteriaQuery.where(idPredicate);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getSingleOrDefault();
    }

    public List<Question> findByTopicId(String topicId) {
        var cb = entityManager.getCriteriaBuilder();
        var cr = cb.createQuery(repositoryClass);

        var message = cr.from(repositoryClass);
        var idPredicate = cb.equal(message.get("topic").get("id"), topicId);
        cr.where(idPredicate);

        var query = entityManager.createQuery(cr);
        return query.getResultList();
    }
}
