package com.example.demo.use.cases.messages.get.by.id;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.MessageRepository;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getMessageById")
public class GetMessageByIdUseCase
        extends BaseUseCase<GetMessageByIdParameters, GetMessageByIdResult> {
    @Autowired
    public GetMessageByIdUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public GetMessageByIdResult executeImpl(
            UnitOfWork unitOfWork,
            GetMessageByIdParameters parameters
    ) {
        var repository = new MessageRepository(unitOfWork);

        if (parameters.isDeleted) setDisableFilter(unitOfWork, "noDeletedMessage");

        var result = new GetMessageByIdResult();
        result.message = repository.findById(parameters.id);
        result.message.ifPresent(message -> Hibernate.initialize(message.author));

        return result;
    }
}