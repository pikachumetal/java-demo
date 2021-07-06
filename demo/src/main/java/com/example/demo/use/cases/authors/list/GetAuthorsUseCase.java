package com.example.demo.use.cases.authors.list;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.AuthorRepository;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getAuthors")
public class GetAuthorsUseCase
        extends BaseUseCase<GetAuthorsParameters, GetAuthorsResult> {
    @Autowired
    public GetAuthorsUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected GetAuthorsResult executeImpl(
            UnitOfWork unitOfWork,
            GetAuthorsParameters parameters
    ) {
        var repository = new AuthorRepository(unitOfWork);

        var result = new GetAuthorsResult();
        result.authors = repository.findAll();

        return result;
    }
}
