package com.example.demo.use.cases.topics.update;

import com.example.demo.domain.Topic;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.TopicRepository;
import com.example.demo.problems.topic.TopicDescriptionExistsProblem;
import com.example.demo.problems.topic.TopicNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("updateTopic")
public class UpdateTopicUseCase
        extends BaseUseCase<UpdateTopicParameters, UpdateTopicResult> {

    @Autowired
    public UpdateTopicUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UpdateTopicResult executeImpl(
            UnitOfWork unitOfWork,
            UpdateTopicParameters parameters
    ) {
        assertTopicExists(unitOfWork, parameters.id);
        assertTopicDescriptionNonExists(unitOfWork, parameters);

        var result = new UpdateTopicResult();
        result.topic = updateTopic(unitOfWork, parameters);
        Hibernate.initialize(result.topic.questions);

        return result;
    }

    private Topic updateTopic(
            UnitOfWork unitOfWork,
            UpdateTopicParameters parameters
    ) {
        var repository = unitOfWork.getTopicRepository();

        var item = repository
                .update(parameters.id,
                        new Topic(parameters.description, parameters.email))
                .orElseThrow();

        return repository.save(item);
    }

    public void assertTopicExists(UnitOfWork unitOfWork, String id) {
        var repository = new TopicRepository(unitOfWork);

        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new TopicNonExistsProblem(id);
    }

    public void assertTopicDescriptionNonExists(
            UnitOfWork unitOfWork,
            UpdateTopicParameters parameters
    ) {
        var repository = unitOfWork.getTopicRepository();

        var item = repository.findByDescription(parameters.description);

        if (item.isEmpty()) return;
        if (item.get().id.equals(parameters.id)) return;

        throw new TopicDescriptionExistsProblem(parameters.description);
    }
}
