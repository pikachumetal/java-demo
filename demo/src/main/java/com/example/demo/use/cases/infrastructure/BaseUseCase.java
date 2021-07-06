package com.example.demo.use.cases.infrastructure;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.GenericProblem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.zalando.problem.ThrowableProblem;

public abstract class BaseUseCase<TIn, TOut> implements UseCase<TIn, TOut> {
    private final SessionFactory sessionFactory;

    public BaseUseCase(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public TOut execute(TIn parameters) throws Exception {
        var unitOfWork = UnitOfWork.createUnitOfWork(sessionFactory.openSession());
        ;
        try (unitOfWork) {
            setDefaultFilters(unitOfWork);

            unitOfWork.begin();
            var result = executeImpl(unitOfWork, parameters);
            unitOfWork.commit();

            return result;
        } catch (ThrowableProblem tp) {
            unitOfWork.rollback();
            throw tp;
        } catch (Exception e) {
            unitOfWork.rollback();
            throw new GenericProblem(e);
        }
    }

    private void setDefaultFilters(UnitOfWork unitOfWork) {
        unitOfWork
                .getEntityManager()
                .unwrap(Session.class)
                .enableFilter("noDeletedAuthor")
                .setParameter("isDeleted", false);
    }

    protected abstract TOut executeImpl(UnitOfWork unitOfWork, TIn parameters);
}
