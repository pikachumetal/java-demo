package com.example.demo.use.cases.questions.get.by.topic.id;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getQuestionsByTopicId")
public class GetQuestionsByTopicIdUseCase
        extends BaseUseCase<GetQuestionsByTopicIdParameters, GetQuestionByTopicIdResult> {
    @Autowired
    public GetQuestionsByTopicIdUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected GetQuestionByTopicIdResult executeImpl(
            UnitOfWork unitOfWork,
            GetQuestionsByTopicIdParameters parameters
    ) {
        var repository = unitOfWork.getQuestionRepository();

        var result = new GetQuestionByTopicIdResult();
        result.questions = repository.findByTopicId(parameters.id);
        result.questions.forEach(o-> Hibernate.initialize(o.topic));

        return result;
    }
}
