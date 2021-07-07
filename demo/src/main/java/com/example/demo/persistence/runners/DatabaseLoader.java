package com.example.demo.persistence.runners;

import com.example.demo.domain.Category;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.CategoryRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("databaseLoader")
public class DatabaseLoader implements CommandLineRunner {
    private final UnitOfWork unitOfWork;

    @Autowired
    public DatabaseLoader(SessionFactory sessionFactory) {
        unitOfWork = UnitOfWork.createUnitOfWork(sessionFactory.openSession());
    }

    @Override
    public void run(String... args) {
        var repository = new CategoryRepository(unitOfWork);

        unitOfWork.begin();

        var author = repository
                .findByDescription("General");

        if (author.isEmpty()) repository
                .save(new Category("General", "frodo.bolson@hobbiton.com"));

        unitOfWork.commit();
    }
}
