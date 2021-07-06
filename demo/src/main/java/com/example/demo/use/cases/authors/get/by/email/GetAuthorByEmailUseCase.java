package com.example.demo.use.cases.authors.get.by.email;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.AuthorRepository;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getAuthorByEmail")
public class GetAuthorByEmailUseCase
        extends BaseUseCase<GetAuthorByEmailParameters, GetAuthorByEmailResult> {
    @Autowired
    public GetAuthorByEmailUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public GetAuthorByEmailResult executeImpl(
            UnitOfWork unitOfWork,
            GetAuthorByEmailParameters parameters
    ) {
        var repository = new AuthorRepository(unitOfWork);

        var result = new GetAuthorByEmailResult();
        result.author = repository.findByEmail(parameters.email);

        return result;
    }
}