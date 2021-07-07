package com.example.demo.use.cases.categories.toggle;

import com.example.demo.domain.Category;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.CategoryRepository;
import com.example.demo.problems.category.CategoryDescriptionExistsProblem;
import com.example.demo.problems.category.CategoryNonExistsProblem;
import com.example.demo.use.cases.categories.update.UpdateCategoryParameters;
import com.example.demo.use.cases.categories.update.UpdateCategoryResult;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("toggleCategory")
public class ToggleCategoryUseCase
        extends BaseUseCase<ToggleCategoryParameters, ToggleCategoryResult> {

    @Autowired
    public ToggleCategoryUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public ToggleCategoryResult executeImpl(
            UnitOfWork unitOfWork,
            ToggleCategoryParameters parameters
    ) {
        assertCategoryExists(unitOfWork, parameters.id);

        var result = new ToggleCategoryResult();
        result.category = toggleCategory(unitOfWork, parameters);
        return result;
    }

    private Category toggleCategory(
            UnitOfWork unitOfWork,
            ToggleCategoryParameters parameters
    ) {
        var repository = unitOfWork.getCategoryRepository();

        var item = repository
                .toggle(parameters.id)
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
