package com.example.demo.use.cases.questions.list;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getQuestions")
public class GetQuestionsUseCase
        extends BaseUseCase<GetQuestionsParameters, GetQuestionsResult> {
    @Autowired
    public GetQuestionsUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected GetQuestionsResult executeImpl(
            UnitOfWork unitOfWork,
            GetQuestionsParameters parameters
    ) {
        var repository = unitOfWork.getQuestionRepository();

        var result = new GetQuestionsResult();
        result.questions = repository.findAll();
        result.questions.forEach(o-> Hibernate.initialize(o.topic));

        return result;
    }
}
