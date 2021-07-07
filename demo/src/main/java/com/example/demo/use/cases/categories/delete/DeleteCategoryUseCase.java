package com.example.demo.use.cases.categories.delete;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.category.CategoryNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deleteCategory")
public class DeleteCategoryUseCase
        extends BaseUseCase<DeleteCategoryParameters, DeleteCategoryResult> {

    @Autowired
    public DeleteCategoryUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public DeleteCategoryResult executeImpl(
            UnitOfWork unitOfWork,
            DeleteCategoryParameters parameters
    ) {
        assertCategoryExists(unitOfWork, parameters.id);
        deleteCategory(unitOfWork, parameters);
        return new DeleteCategoryResult();
    }

    private void deleteCategory(UnitOfWork unitOfWork, DeleteCategoryParameters parameters) {
        var repository = unitOfWork.getCategoryRepository();
        repository.delete(parameters.id);
    }

    public void assertCategoryExists(UnitOfWork unitOfWork, String id) {
        var repository = unitOfWork.getCategoryRepository();
        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new CategoryNonExistsProblem(id);
    }
}
