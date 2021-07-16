package com.example.demo.use.cases.topics.delete;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.topic.TopicNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deleteTopic")
public class DeleteTopicUseCase
        extends BaseUseCase<DeleteTopicParameters, DeleteTopicResult> {

    @Autowired
    public DeleteTopicUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public DeleteTopicResult executeImpl(
            UnitOfWork unitOfWork,
            DeleteTopicParameters parameters
    ) {
        assertTopicExists(unitOfWork, parameters.id);
        deleteTopic(unitOfWork, parameters);
        return new DeleteTopicResult();
    }

    private void deleteTopic(UnitOfWork unitOfWork, DeleteTopicParameters parameters) {
        var repository = unitOfWork.getTopicRepository();
        repository.delete(parameters.id);
    }

    public void assertTopicExists(UnitOfWork unitOfWork, String id) {
        var repository = unitOfWork.getTopicRepository();
        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new TopicNonExistsProblem(id);
    }
}
