package com.example.demo.persistence.repositories;

import com.example.demo.domain.Question;
import com.example.demo.domain.Topic;
import com.example.demo.persistence.UnitOfWork;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class QuestionRepository extends BaseRepository<Question, String> {
    public QuestionRepository(UnitOfWork unitOfWork) {
        super(Question.class, unitOfWork);
    }

    @Modifying
    public Optional<Question> toggle(String id) {
        var item = findById(id).orElseThrow();
        item.active = !item.active;
        return Optional.of(item);
    }

    @Modifying
    public Optional<Question> update(String id, Question item) {
        var oldItem = findById(id).orElseThrow();

        oldItem.query = item.query;
        oldItem.answers = item.answers;
        oldItem.email = item.email;

        return Optional.of(oldItem);
    }

    public Optional<Question> findByQuery(String topicId, String text) {
        var cb = entityManager.getCriteriaBuilder();
        var cr = cb.createQuery(repositoryClass);

        var root = cr.from(repositoryClass);

        var predicates = new LinkedList<Predicate>();
        predicates.add(cb.equal(root.get("topic").get("id"), topicId));
        predicates.add(cb.equal(root.get("query"), text));

        cr.where(predicates.toArray(new Predicate[0]));

        var query = entityManager.createQuery(cr);
        return query.getSingleOrDefault();
    }

    public List<Question> findByTopicId(String topicId) {
        var cb = entityManager.getCriteriaBuilder();
        var cr = cb.createQuery(repositoryClass);

        var root = cr.from(repositoryClass);

        var predicates = new LinkedList<Predicate>();
        predicates.add(cb.equal(root.get("topic").get("id"), topicId));

        cr.where(predicates.toArray(new Predicate[0]));

        var query = entityManager.createQuery(cr);
        return query.getResultList();
    }
}
