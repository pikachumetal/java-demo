package com.example.demo.persistence.repositories;

import com.example.demo.domain.Author;
import com.example.demo.domain.Message;
import com.example.demo.persistence.UnitOfWork;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;

public class MessageRepository extends BaseRepository<Message, String> {
    public MessageRepository(UnitOfWork unitOfWork) {
        super(Message.class, unitOfWork);
    }

    public List<Message> findByAuthorId(String authorId) {
        var cb = entityManager.getCriteriaBuilder();
        var cr = cb.createQuery(repositoryClass);

        var message = cr.from(repositoryClass);
        var idPredicate = cb.equal(message.get("id"), authorId);
        cr.where(idPredicate);

        var query = entityManager.createQuery(cr);
        return query.getResultList();
    }
}
