package com.example.demo.persistence.repositories;

import com.example.demo.domain.Category;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.category.CategoryNonExistsProblem;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public class CategoryRepository extends BaseRepository<Category,String>{

    public CategoryRepository(UnitOfWork unitOfWork) {
        super(Category.class, unitOfWork);
    }

    @Modifying
    public Optional<Category> toggle(String id){
        var category = findById(id).orElseThrow();
        category.active = !category.active;
        return Optional.of(category);
    }

    @Modifying
    public Optional<Category> update(String id, Category category){
        var oldCategory = findById(id).orElseThrow();

        oldCategory.description = category.description;
        oldCategory.email = category.email;

        return Optional.of(oldCategory);
    }

    public Optional<Category> findByEmail(String email) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(repositoryClass);

        var author = criteriaQuery.from(repositoryClass);

        var idPredicate = criteriaBuilder.equal(author.get("email"), email);
        criteriaQuery.where(idPredicate);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getSingleOrDefault();
    }

    public Optional<Category> findByDescription(String description) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(repositoryClass);

        var author = criteriaQuery.from(repositoryClass);

        var idPredicate = criteriaBuilder.equal(author.get("description"), description);
        criteriaQuery.where(idPredicate);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getSingleOrDefault();
    }
}
