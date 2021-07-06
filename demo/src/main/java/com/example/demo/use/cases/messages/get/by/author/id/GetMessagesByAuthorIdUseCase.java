package com.example.demo.use.cases.messages.get.by.author.id;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.MessageRepository;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getMessagesByAuthorId")
public class GetMessagesByAuthorIdUseCase
        extends BaseUseCase<GetMessagesByAuthorIdParameters, GetMessagesByAuthorIdResult> {
    @Autowired
    public GetMessagesByAuthorIdUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected GetMessagesByAuthorIdResult executeImpl(
            UnitOfWork unitOfWork,
            GetMessagesByAuthorIdParameters parameters
    ) {
        var repository = new MessageRepository(unitOfWork);

        var result = new GetMessagesByAuthorIdResult();
        result.messages = repository.findByAuthorId(parameters.id);
        result.messages.forEach(o-> Hibernate.initialize(o.author));

        return result;
    }
}
