package com.example.demo.use.cases.messages.list;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.MessageRepository;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("getMessages")
public class GetMessagesUseCase
        extends BaseUseCase<GetMessagesParameters, GetMessagesResult> {
    @Autowired
    public GetMessagesUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected GetMessagesResult executeImpl(
            UnitOfWork unitOfWork,
            GetMessagesParameters parameters
    ) {
        var repository = new MessageRepository(unitOfWork);

        var result = new GetMessagesResult();
        result.messages = repository.findAll();
        result.messages.forEach(o-> Hibernate.initialize(o.author));

        return result;
    }
}
