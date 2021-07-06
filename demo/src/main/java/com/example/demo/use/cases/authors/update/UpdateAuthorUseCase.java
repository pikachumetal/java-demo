package com.example.demo.use.cases.authors.update;

import com.example.demo.domain.Author;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.AuthorRepository;
import com.example.demo.problems.AuthorEmailExistsProblem;
import com.example.demo.problems.AuthorNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("updateAuthor")
public class UpdateAuthorUseCase
        extends BaseUseCase<UpdateAuthorParameters, UpdateAuthorResult> {

    @Autowired
    public UpdateAuthorUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UpdateAuthorResult executeImpl(
            UnitOfWork unitOfWork,
            UpdateAuthorParameters parameters
    ) {
        assertAuthorExists(unitOfWork, parameters.id);
        assertAuthorEmailNonExists(unitOfWork, parameters);

        var result = new UpdateAuthorResult();
        result.author = updateAuthor(unitOfWork, parameters);
        return result;
    }

    private Author updateAuthor(
            UnitOfWork unitOfWork,
            UpdateAuthorParameters parameters
    ) {
        var repository = new AuthorRepository(unitOfWork);

        var item = repository
                .findById(parameters.id)
                .orElseThrow();

        item.email = parameters.email;
        item.firstName = parameters.firstName;
        item.lastName = parameters.lastName;

        return repository.save(item);
    }

    public void assertAuthorExists(UnitOfWork unitOfWork, String id) {
        var repository = new AuthorRepository(unitOfWork);
        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new AuthorNonExistsProblem(id);
    }

    public void assertAuthorEmailNonExists(UnitOfWork unitOfWork, UpdateAuthorParameters parameters) {
        var repository = new AuthorRepository(unitOfWork);
        var item = repository.findByEmail(parameters.email);

        if (item.isEmpty()) return;
        if (item.get().id.equals(parameters.id)) return;

        throw new AuthorEmailExistsProblem(parameters.email);
    }
}
