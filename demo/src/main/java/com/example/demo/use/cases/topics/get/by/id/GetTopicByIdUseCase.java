package com.example.demo.use.cases.topics.get.by.id;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getTopicById")
public class GetTopicByIdUseCase
        extends BaseUseCase<GetTopicByIdParameters, GetTopicByIdResult> {
    @Autowired
    public GetTopicByIdUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public GetTopicByIdResult executeImpl(
            UnitOfWork unitOfWork,
            GetTopicByIdParameters parameters
    ) {
        var repository = unitOfWork.getTopicRepository();

        if (parameters.showDeleted) setDisableFilter(unitOfWork, "noDeletedTopic");

        var result = new GetTopicByIdResult();
        result.topic = repository.findById(parameters.id);
        result.topic.ifPresent(o -> Hibernate.initialize(o.questions));

        return result;
    }
}