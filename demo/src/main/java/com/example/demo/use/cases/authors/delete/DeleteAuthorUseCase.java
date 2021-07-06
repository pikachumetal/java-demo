package com.example.demo.use.cases.authors.delete;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.AuthorRepository;
import com.example.demo.problems.AuthorNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deleteAuthor")
public class DeleteAuthorUseCase
        extends BaseUseCase<DeleteAuthorParameters, DeleteAuthorResult> {

    @Autowired
    public DeleteAuthorUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public DeleteAuthorResult executeImpl(
            UnitOfWork unitOfWork,
            DeleteAuthorParameters parameters
    ) {
        assertAuthorExists(unitOfWork, parameters.id);
        deleteAuthor(unitOfWork, parameters);
        return new DeleteAuthorResult();
    }

    private void deleteAuthor(UnitOfWork unitOfWork, DeleteAuthorParameters parameters) {
        var repository = new AuthorRepository(unitOfWork);
        var item = repository.findById(parameters.id);

        repository.delete(parameters.id);
    }

    public void assertAuthorExists(UnitOfWork unitOfWork, String id) {
        var repository = new AuthorRepository(unitOfWork);
        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new AuthorNonExistsProblem(id);
    }
}
