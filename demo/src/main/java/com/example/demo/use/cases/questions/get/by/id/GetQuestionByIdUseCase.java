package com.example.demo.use.cases.questions.get.by.id;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getQuestionById")
public class GetQuestionByIdUseCase
        extends BaseUseCase<GetQuestionByIdParameters, GetQuestionByIdResult> {
    @Autowired
    public GetQuestionByIdUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public GetQuestionByIdResult executeImpl(
            UnitOfWork unitOfWork,
            GetQuestionByIdParameters parameters
    ) {
        var repository = unitOfWork.getQuestionRepository();

        if (parameters.showDeleted) setDisableFilter(unitOfWork, "noDeletedQuestion");

        var result = new GetQuestionByIdResult();
        result.question = repository.findById(parameters.id);
        result.question.ifPresent(o -> Hibernate.initialize(o.topic));

        return result;
    }
}