package com.example.demo.use.cases.infrastructure;

import com.example.demo.domain.Author;
import com.example.demo.domain.Message;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.problems.GenericProblem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.zalando.problem.ThrowableProblem;

import javax.persistence.Entity;
import java.util.Arrays;

public abstract class BaseUseCase<TIn, TOut> implements UseCase<TIn, TOut> {
    private final SessionFactory sessionFactory;

    @Autowired
    private ApplicationContext context;

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

    public void setDisableFilter(UnitOfWork unitOfWork, String filterName) {
        var session = unitOfWork
                .getEntityManager()
                .unwrap(Session.class);

        session.disableFilter(filterName);
    }

    private void setDefaultFilters(UnitOfWork unitOfWork) {
        var session = unitOfWork
                .getEntityManager()
                .unwrap(Session.class);

        var filters = Arrays.asList(
                Author.class.getSimpleName(),
                Message.class.getSimpleName()
        );

        filters.forEach(o -> session
                .enableFilter("noDeleted%s".formatted(o))
                .setParameter("isDeleted", false));
    }

    protected abstract TOut executeImpl(UnitOfWork unitOfWork, TIn parameters);
}
