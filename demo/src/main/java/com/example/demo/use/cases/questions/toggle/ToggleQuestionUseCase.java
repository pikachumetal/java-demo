package com.example.demo.use.cases.questions.toggle;

import com.example.demo.domain.Question;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.QuestionRepository;
import com.example.demo.problems.question.QuestionNonExistsProblem;
import com.example.demo.problems.question.QuestionQueryExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import com.example.demo.use.cases.questions.update.UpdateQuestionParameters;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("toggleQuestion")
public class ToggleQuestionUseCase
        extends BaseUseCase<ToggleQuestionParameters, ToggleQuestionResult> {

    @Autowired
    public ToggleQuestionUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public ToggleQuestionResult executeImpl(
            UnitOfWork unitOfWork,
            ToggleQuestionParameters parameters
    ) {
        assertQuestionExists(unitOfWork, parameters.id);

        var result = new ToggleQuestionResult();
        result.question = toggleQuestion(unitOfWork, parameters);
        Hibernate.initialize(result.question.topic);

        return result;
    }

    private Question toggleQuestion(
            UnitOfWork unitOfWork,
            ToggleQuestionParameters parameters
    ) {
        var repository = unitOfWork.getQuestionRepository();

        var item = repository
                .toggle(parameters.id)
                .orElseThrow();

        return repository.save(item);
    }

    public void assertQuestionExists(UnitOfWork unitOfWork, String id) {
        var repository = new QuestionRepository(unitOfWork);

        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new QuestionNonExistsProblem(id);
    }
}
