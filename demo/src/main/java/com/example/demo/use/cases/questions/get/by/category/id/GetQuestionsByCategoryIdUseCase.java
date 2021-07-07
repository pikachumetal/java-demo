package com.example.demo.use.cases.questions.get.by.category.id;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getQuestionsByCategoryId")
public class GetQuestionsByCategoryIdUseCase
        extends BaseUseCase<GetQuestionsByCategoryIdParameters, GetQuestionByCategoryIdResult> {
    @Autowired
    public GetQuestionsByCategoryIdUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected GetQuestionByCategoryIdResult executeImpl(
            UnitOfWork unitOfWork,
            GetQuestionsByCategoryIdParameters parameters
    ) {
        var repository = unitOfWork.getQuestionRepository();

        var result = new GetQuestionByCategoryIdResult();
        result.questions = repository.findByCategoryId(parameters.id);
        result.questions.forEach(o-> Hibernate.initialize(o.category));

        return result;
    }
}
