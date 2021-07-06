package com.example.demo.persistence.repositories;

import com.example.demo.domain.Author;
import com.example.demo.persistence.UnitOfWork;

import java.util.Optional;

public class AuthorRepository extends BaseRepository<Author,String>{

    public AuthorRepository(UnitOfWork unitOfWork) {
        super(Author.class, unitOfWork);
    }

    public Optional<Author> findByEmail(String email) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(repositoryClass);

        var author = criteriaQuery.from(repositoryClass);

        var idPredicate = criteriaBuilder.equal(author.get("email"), email);
        criteriaQuery.where(idPredicate);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getSingleOrDefault();
    }
}
