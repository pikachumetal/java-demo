package com.example.demo.use.cases.categories.update;

import com.example.demo.domain.Category;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.CategoryRepository;
import com.example.demo.problems.category.CategoryDescriptionExistsProblem;
import com.example.demo.problems.category.CategoryNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("updateCategory")
public class UpdateCategoryUseCase
        extends BaseUseCase<UpdateCategoryParameters, UpdateCategoryResult> {

    @Autowired
    public UpdateCategoryUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UpdateCategoryResult executeImpl(
            UnitOfWork unitOfWork,
            UpdateCategoryParameters parameters
    ) {
        assertCategoryExists(unitOfWork, parameters.id);
        assertCategoryDescriptionNonExists(unitOfWork, parameters);

        var result = new UpdateCategoryResult();
        result.category = updateCategory(unitOfWork, parameters);
        return result;
    }

    private Category updateCategory(
            UnitOfWork unitOfWork,
            UpdateCategoryParameters parameters
    ) {
        var repository = unitOfWork.getCategoryRepository();

        var item = repository
                .update(parameters.id, new Category(parameters.description, parameters.email))
                .orElseThrow();

        return repository.save(item);
    }

    public void assertCategoryExists(UnitOfWork unitOfWork, String id) {
        var repository = new CategoryRepository(unitOfWork);

        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new CategoryNonExistsProblem(id);
    }

    public void assertCategoryDescriptionNonExists(
            UnitOfWork unitOfWork,
            UpdateCategoryParameters parameters
    ) {
        var repository = unitOfWork.getCategoryRepository();

        var item = repository.findByDescription(parameters.description);

        if (item.isEmpty()) return;
        if (item.get().id.equals(parameters.id)) return;

        throw new CategoryDescriptionExistsProblem(parameters.description);
    }
}
