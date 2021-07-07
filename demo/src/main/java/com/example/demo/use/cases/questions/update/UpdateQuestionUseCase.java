package com.example.demo.use.cases.questions.update;

import com.example.demo.domain.Question;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.question.QuestionNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
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

        var result = new UpdateQuestionResult();
        result.question = updateMessage(unitOfWork, parameters);
        Hibernate.initialize(result.question.category);

        return result;
    }

    private Question updateMessage(
            UnitOfWork unitOfWork,
            UpdateQuestionParameters parameters
    ) {
        var repository = unitOfWork.getQuestionRepository();

        var item = repository
                .findById(parameters.id)
                .orElseThrow();

        item.query = parameters.query;
        item.email = parameters.email;

        return repository.save(item);
    }

    public void assertQuestionExists(UnitOfWork unitOfWork, String id) {
        var repository = unitOfWork.getQuestionRepository();

        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new QuestionNonExistsProblem(id);
    }
}
