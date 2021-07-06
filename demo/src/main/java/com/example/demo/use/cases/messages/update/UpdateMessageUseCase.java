package com.example.demo.use.cases.messages.update;

import com.example.demo.domain.Message;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.MessageRepository;
import com.example.demo.problems.message.MessageNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("updateMessage")
public class UpdateMessageUseCase
        extends BaseUseCase<UpdateMessageParameters, UpdateMessageResult> {

    @Autowired
    public UpdateMessageUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UpdateMessageResult executeImpl(
            UnitOfWork unitOfWork,
            UpdateMessageParameters parameters
    ) {
        assertMessageExists(unitOfWork, parameters.id);

        var result = new UpdateMessageResult();
        result.message = updateMessage(unitOfWork, parameters);
        Hibernate.initialize(result.message.author);

        return result;
    }

    private Message updateMessage(
            UnitOfWork unitOfWork,
            UpdateMessageParameters parameters
    ) {
        var repository = new MessageRepository(unitOfWork);

        var item = repository
                .findById(parameters.id)
                .orElseThrow();

        item.title = parameters.title;
        item.message = parameters.message;

        return repository.save(item);
    }

    public void assertMessageExists(UnitOfWork unitOfWork, String id) {
        var repository = new MessageRepository(unitOfWork);
        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new MessageNonExistsProblem(id);
    }
}
