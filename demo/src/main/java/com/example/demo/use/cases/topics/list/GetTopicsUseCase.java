package com.example.demo.use.cases.topics.list;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getTopics")
public class GetTopicsUseCase
        extends BaseUseCase<GetTopicsParameters, GetTopicsResult> {
    @Autowired
    public GetTopicsUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected GetTopicsResult executeImpl(
            UnitOfWork unitOfWork,
            GetTopicsParameters parameters
    ) {
        var repository = unitOfWork.getTopicRepository();

        var result = new GetTopicsResult();
        result.topics = repository.findAll();
        result.topics.forEach(o-> Hibernate.initialize(o.questions));

        return result;
    }
}
