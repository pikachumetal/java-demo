package com.example.demo.use.cases.categories.get.by.id;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getCategoryById")
public class GetCategoryByIdUseCase
        extends BaseUseCase<GetCategoryByIdParameters, GetCategoryByIdResult> {
    @Autowired
    public GetCategoryByIdUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public GetCategoryByIdResult executeImpl(
            UnitOfWork unitOfWork,
            GetCategoryByIdParameters parameters
    ) {
        var repository = unitOfWork.getCategoryRepository();

        if (parameters.showDeleted) setDisableFilter(unitOfWork, "noDeletedCategory");

        var result = new GetCategoryByIdResult();
        result.category = repository.findById(parameters.id);
        return result;
    }
}