package com.example.demo.use.cases.questions.add;

import com.example.demo.domain.Topic;
import com.example.demo.domain.Question;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.topic.TopicInactiveProblem;
import com.example.demo.problems.topic.TopicNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("addQuestion")
public class AddQuestionUseCase
        extends BaseUseCase<AddQuestionParameters, AddQuestionResult> {

    @Autowired
    public AddQuestionUseCase(
            SessionFactory sessionFactory
    ) {
        super(sessionFactory);
    }

    @Override
    public AddQuestionResult executeImpl(
            UnitOfWork unitOfWork,
            AddQuestionParameters parameters
    ) {
        assertTopicExists(unitOfWork, parameters.topicId);
        assertTopicIsActive(unitOfWork, parameters.topicId);

        var result = new AddQuestionResult();
        result.question = addQuestion(unitOfWork, parameters);
        return result;
    }

    private Question addQuestion(
            UnitOfWork unitOfWork,
            AddQuestionParameters parameters
    ) {
        var repository = unitOfWork.getQuestionRepository();

        var item = new Question(parameters.query, parameters.email);
        item.answers = parameters.answers;
        item.topic = getTopicById(unitOfWork, parameters.topicId);

        return repository.save(item);
    }

    private Topic getTopicById(UnitOfWork unitOfWork, String id) {
        var repository = unitOfWork.getTopicRepository();
        return repository.findById(id).orElseThrow();
    }

    public void assertTopicExists(
            UnitOfWork unitOfWork,
            String topicId
    ) {
        var repository = unitOfWork.getTopicRepository();
        var item = repository.findById(topicId);

        if (item.isPresent()) return;
        throw new TopicNonExistsProblem(topicId);
    }

    public void assertTopicIsActive(
            UnitOfWork unitOfWork,
            String topicId
    ) {
        var repository = unitOfWork.getTopicRepository();
        var item = repository.findById(topicId).orElseThrow();

        if (item.active) return;
        throw new TopicInactiveProblem(topicId);
    }
}
