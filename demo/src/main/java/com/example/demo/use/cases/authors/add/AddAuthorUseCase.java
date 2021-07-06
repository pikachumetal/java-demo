package com.example.demo.use.cases.authors.add;

import com.example.demo.domain.Author;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.AuthorRepository;
import com.example.demo.problems.AuthorEmailExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("addAuthor")
public class AddAuthorUseCase
        extends BaseUseCase<AddAuthorParameters, AddAuthorResult> {
    private final ModelMapper modelMapper;

    @Autowired
    public AddAuthorUseCase(
            SessionFactory sessionFactory,
            ModelMapper modelMapper
    ) {
        super(sessionFactory);
        this.modelMapper = modelMapper;
    }

    @Override
    public AddAuthorResult executeImpl(
            UnitOfWork unitOfWork,
            AddAuthorParameters parameters
    ) {
        assertAuthorExists(unitOfWork, parameters.email);

        var result = new AddAuthorResult();
        result.author = addAuthor(unitOfWork, parameters);
        return result;
    }

    private Author addAuthor(
            UnitOfWork unitOfWork,
            AddAuthorParameters parameters
    ) {
        var repository = new AuthorRepository(unitOfWork);
        var item = modelMapper.map(parameters, Author.class);
        return repository.save(item);
    }

    private Optional<Author> getAuthorById(
            UnitOfWork unitOfWork,
            String id
    ) {
        var repository = new AuthorRepository(unitOfWork);
        return repository.findById(id);
    }

    public void assertAuthorExists(
            UnitOfWork unitOfWork,
            String email
    ) {
        var repository = new AuthorRepository(unitOfWork);
        var item = repository.findByEmail(email);

        if (item.isEmpty()) return;
        throw new AuthorEmailExistsProblem(email);
    }
}
