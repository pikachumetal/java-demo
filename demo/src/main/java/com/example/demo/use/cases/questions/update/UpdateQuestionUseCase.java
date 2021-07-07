package com.example.demo.use.cases.questions.update;

import com.example.demo.domain.Question;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.question.QuestionNonExistsProblem;
import com.example.demo.problems.question.QuestionQueryExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import com.example.demo.use.cases.questions.add.AddQuestionParameters;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("updateQuestion")
public class UpdateQuestionUseCase
        extends BaseUseCase<UpdateQuestionParameters, UpdateQuestionResult> {

    @Autowired
    public UpdateQuestionUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UpdateQuestionResult executeImpl(
            UnitOfWork unitOfWork,
            UpdateQuestionParameters parameters
    ) {
        assertQuestionExists(unitOfWork, parameters.id);
        assertQuestionQueryNonExists(unitOfWork, parameters);

        var result = new UpdateQuestionResult();
        result.question = updateMessage(unitOfWork, parameters);
        Hibernate.initialize(result.question.topic);

        return result;
    }

    private Question updateMessage(
            UnitOfWork unitOfWork,
            UpdateQuestionParameters parameters
    ) {
        var repository = unitOfWork.getQuestionRepository();

        var item = repository
                .update(parameters.id,
                        new Question(parameters.query, parameters.email, parameters.answers))
                .orElseThrow();

        return repository.save(item);
    }

    public void assertQuestionExists(UnitOfWork unitOfWork, String id) {
        var repository = unitOfWork.getQuestionRepository();

        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new QuestionNonExistsProblem(id);
    }

    public void assertQuestionQueryNonExists(
            UnitOfWork unitOfWork,
            UpdateQuestionParameters parameters
    ) {
        var repository = unitOfWork.getQuestionRepository();

        var oldQuestion = repository.findById(parameters.id);
        var item = repository
                .findByQuery(oldQuestion.orElseThrow().topic.id, parameters.query);

        if (item.isEmpty()) return;
        if (item.get().id.equals(parameters.id)) return;

        throw new QuestionQueryExistsProblem(parameters.query);
    }
}
