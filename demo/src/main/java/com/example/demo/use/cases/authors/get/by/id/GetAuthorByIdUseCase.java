package com.example.demo.use.cases.authors.get.by.id;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.AuthorRepository;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getAuthorById")
public class GetAuthorByIdUseCase
        extends BaseUseCase<GetAuthorByIdParameters, GetAuthorByIdResult> {
    @Autowired
    public GetAuthorByIdUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public GetAuthorByIdResult executeImpl(
            UnitOfWork unitOfWork,
            GetAuthorByIdParameters parameters
    ) {
        var repository = new AuthorRepository(unitOfWork);

        var result = new GetAuthorByIdResult();
        result.author = repository.findById(parameters.id);

        return result;
    }
}