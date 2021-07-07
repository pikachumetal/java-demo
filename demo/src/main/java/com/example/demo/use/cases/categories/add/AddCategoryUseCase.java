package com.example.demo.use.cases.categories.add;

import com.example.demo.domain.Category;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.category.CategoryDescriptionExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("addCategory")
public class AddCategoryUseCase
        extends BaseUseCase<AddCategoryParameters, AddCategoryResult> {

    @Autowired
    public AddCategoryUseCase(
            SessionFactory sessionFactory
    ) {
        super(sessionFactory);
    }

    @Override
    public AddCategoryResult executeImpl(
            UnitOfWork unitOfWork,
            AddCategoryParameters parameters
    ) {
        assertCategoryExists(unitOfWork, parameters.description);

        var result = new AddCategoryResult();
        result.category = addCategory(unitOfWork, parameters);
        return result;
    }

    private Category addCategory(
            UnitOfWork unitOfWork,
            AddCategoryParameters parameters
    ) {
        var repository = unitOfWork.getCategoryRepository();
        var item = new Category(parameters.description, parameters.email);
        return repository.save(item);
    }

    public void assertCategoryExists(
            UnitOfWork unitOfWork,
            String description
    ) {
        var repository = unitOfWork.getCategoryRepository();
        var item = repository.findByDescription(description);

        if (item.isEmpty()) return;
        throw new CategoryDescriptionExistsProblem(description);
    }
}
