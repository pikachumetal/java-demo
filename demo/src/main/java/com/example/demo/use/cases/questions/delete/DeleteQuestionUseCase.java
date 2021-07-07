package com.example.demo.use.cases.questions.delete;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.question.QuestionNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deleteQuestion")
public class DeleteQuestionUseCase
        extends BaseUseCase<DeleteQuestionParameters, DeleteQuestionResult> {

    @Autowired
    public DeleteQuestionUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public DeleteQuestionResult executeImpl(
            UnitOfWork unitOfWork,
            DeleteQuestionParameters parameters
    ) {
        assertQuestionExists(unitOfWork, parameters.id);
        deleteQuestion(unitOfWork, parameters);
        return new DeleteQuestionResult();
    }

    private void deleteQuestion(UnitOfWork unitOfWork, DeleteQuestionParameters parameters) {
        var repository = unitOfWork.getQuestionRepository();
        repository.delete(parameters.id);
    }

    public void assertQuestionExists(UnitOfWork unitOfWork, String id) {
        var repository = unitOfWork.getQuestionRepository();

        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new QuestionNonExistsProblem(id);
    }
}
