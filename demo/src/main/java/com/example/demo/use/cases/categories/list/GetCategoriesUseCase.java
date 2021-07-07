package com.example.demo.use.cases.categories.list;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getCategories")
public class GetCategoriesUseCase
        extends BaseUseCase<GetCategoriesParameters, GetCategoriesResult> {
    @Autowired
    public GetCategoriesUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected GetCategoriesResult executeImpl(
            UnitOfWork unitOfWork,
            GetCategoriesParameters parameters
    ) {
        var repository = unitOfWork.getCategoryRepository();

        var result = new GetCategoriesResult();
        result.categories = repository.findAll();
        return result;
    }
}
