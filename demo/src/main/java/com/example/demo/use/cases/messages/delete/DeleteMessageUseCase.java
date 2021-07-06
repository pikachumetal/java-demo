package com.example.demo.use.cases.messages.delete;

import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.MessageRepository;
import com.example.demo.problems.message.MessageNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deleteMessage")
public class DeleteMessageUseCase
        extends BaseUseCase<DeleteMessageParameters, DeleteMessageResult> {

    @Autowired
    public DeleteMessageUseCase(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public DeleteMessageResult executeImpl(
            UnitOfWork unitOfWork,
            DeleteMessageParameters parameters
    ) {
        assertMessageExists(unitOfWork, parameters.id);
        deleteMessage(unitOfWork, parameters);
        return new DeleteMessageResult();
    }

    private void deleteMessage(UnitOfWork unitOfWork, DeleteMessageParameters parameters) {
        var repository = new MessageRepository(unitOfWork);
        var item = repository.findById(parameters.id);

        repository.delete(parameters.id);
    }

    public void assertMessageExists(UnitOfWork unitOfWork, String id) {
        var repository = new MessageRepository(unitOfWork);
        var item = repository.findById(id);

        if (item.isPresent()) return;
        throw new MessageNonExistsProblem(id);
    }
}
