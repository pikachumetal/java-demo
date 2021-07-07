package com.example.demo.use.cases.questions.add;

import com.example.demo.domain.Category;
import com.example.demo.domain.Question;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.category.CategoryNonExistsProblem;
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
        assertCategoryExists(unitOfWork, parameters.categoryId);

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
        item.category = getCategoryById(unitOfWork, parameters.categoryId);

        return repository.save(item);
    }

    private Category getCategoryById(UnitOfWork unitOfWork, String id) {
        var repository = unitOfWork.getCategoryRepository();
        return repository.findById(id).orElseThrow();
    }

    public void assertCategoryExists(
            UnitOfWork unitOfWork,
            String categoryId
    ) {
        var repository = unitOfWork.getCategoryRepository();
        var item = repository.findById(categoryId);

        if (item.isPresent()) return;
        throw new CategoryNonExistsProblem(categoryId);
    }
}
