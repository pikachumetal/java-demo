package com.example.demo.use.cases.messages.add;

import com.example.demo.domain.Author;
import com.example.demo.domain.Message;
import com.example.demo.persistence.UnitOfWork;
import com.example.demo.persistence.repositories.AuthorRepository;
import com.example.demo.persistence.repositories.MessageRepository;
import com.example.demo.problems.author.AuthorNonExistsProblem;
import com.example.demo.use.cases.infrastructure.BaseUseCase;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("addMessage")
public class AddMessageUseCase
        extends BaseUseCase<AddMessageParameters, AddMessageResult> {
    private final ModelMapper modelMapper;

    @Autowired
    public AddMessageUseCase(
            SessionFactory sessionFactory,
            ModelMapper modelMapper
    ) {
        super(sessionFactory);
        this.modelMapper = modelMapper;
    }

    @Override
    public AddMessageResult executeImpl(
            UnitOfWork unitOfWork,
            AddMessageParameters parameters
    ) {
        assertAuthorExists(unitOfWork, parameters.authorId);

        var result = new AddMessageResult();
        result.message = addMessage(unitOfWork, parameters);
        return result;
    }

    private Message addMessage(
            UnitOfWork unitOfWork,
            AddMessageParameters parameters
    ) {
        var repository = new MessageRepository(unitOfWork);

        var item = new Message(parameters.title, parameters.message);
        item.author = getAuthorById(unitOfWork, parameters.authorId);

        return repository.save(item);
    }

    private Author getAuthorById(UnitOfWork unitOfWork, String id) {
        var repository = new AuthorRepository(unitOfWork);
        return repository.findById(id).orElseThrow();
    }

    public void assertAuthorExists(
            UnitOfWork unitOfWork,
            String authorId
    ) {
        var repository = new AuthorRepository(unitOfWork);
        var item = repository.findById(authorId);

        if (item.isPresent()) return;
        throw new AuthorNonExistsProblem(authorId);
    }
}
