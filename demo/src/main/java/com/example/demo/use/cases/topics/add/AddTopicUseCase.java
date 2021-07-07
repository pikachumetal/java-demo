package com.example.demo.use.cases.topics.add;

import com.example.demo.domain.Topic;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.topic.TopicDescriptionExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("addTopic")
public class AddTopicUseCase
        extends BaseUseCase<AddTopicParameters, AddTopicResult> {

    @Autowired
    public AddTopicUseCase(
            SessionFactory sessionFactory
    ) {
        super(sessionFactory);
    }

    @Override
    public AddTopicResult executeImpl(
            UnitOfWork unitOfWork,
            AddTopicParameters parameters
    ) {
        assertTopicExists(unitOfWork, parameters.description);

        var result = new AddTopicResult();
        result.topic = addTopic(unitOfWork, parameters);
        return result;
    }

    private Topic addTopic(
            UnitOfWork unitOfWork,
            AddTopicParameters parameters
    ) {
        var repository = unitOfWork.getTopicRepository();
        var item = new Topic(parameters.description, parameters.email);
        return repository.save(item);
    }

    public void assertTopicExists(
            UnitOfWork unitOfWork,
            String description
    ) {
        var repository = unitOfWork.getTopicRepository();
        var item = repository.findByDescription(description);

        if (item.isEmpty()) return;
        throw new TopicDescriptionExistsProblem(description);
    }
}
