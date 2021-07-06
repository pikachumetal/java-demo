package com.example.demo.controllers;

import com.example.demo.contracts.messages.add.message.AddMessageRequest;
import com.example.demo.contracts.messages.common.MessageResponse;
import com.example.demo.contracts.messages.common.MessagesResponse;
import com.example.demo.contracts.messages.update.message.UpdateMessageRequest;
import com.example.demo.problems.GenericProblem;
import com.example.demo.use.cases.messages.add.AddMessageParameters;
import com.example.demo.use.cases.messages.add.AddMessageUseCase;
import com.example.demo.use.cases.messages.delete.DeleteMessageParameters;
import com.example.demo.use.cases.messages.delete.DeleteMessageUseCase;
import com.example.demo.use.cases.messages.get.by.author.id.GetMessagesByAuthorIdParameters;
import com.example.demo.use.cases.messages.get.by.author.id.GetMessagesByAuthorIdUseCase;
import com.example.demo.use.cases.messages.get.by.id.GetMessageByIdParameters;
import com.example.demo.use.cases.messages.get.by.id.GetMessageByIdUseCase;
import com.example.demo.use.cases.messages.list.GetMessagesParameters;
import com.example.demo.use.cases.messages.list.GetMessagesUseCase;
import com.example.demo.use.cases.messages.update.UpdateMessageParameters;
import com.example.demo.use.cases.messages.update.UpdateMessageUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/messages")
@SuppressWarnings("unused")
public class MessagesController {

    @SuppressWarnings("unused")
    @Autowired
    private ModelMapper modelMapper;

    @SuppressWarnings("unused")
    @Autowired
    private GetMessagesUseCase getMessagesUseCase;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<MessagesResponse> getMessages() throws Exception {
        var parameters = new GetMessagesParameters();

        var result = getMessagesUseCase.execute(parameters);

        var response = new MessagesResponse();
        response.messages = result.messages.stream()
                .map(o -> modelMapper.map(o, MessageResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private GetMessagesByAuthorIdUseCase getMessagesByAuthorIdUseCase;

    @RequestMapping(value = "/authors/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<MessagesResponse> getMessagesByAuthorId(
            @PathVariable("id") String id
    ) throws Exception {
        var parameters = new GetMessagesByAuthorIdParameters();
        parameters.id = id;

        var result = getMessagesByAuthorIdUseCase.execute(parameters);

        var response = new MessagesResponse();
        response.messages = result.messages.stream()
                .map(o -> modelMapper.map(o, MessageResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private GetMessageByIdUseCase getMessageByIdUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<MessageResponse> getMessageById(
            @PathVariable("id") String id,
            @RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted
    ) throws Exception {
        var parameters = new GetMessageByIdParameters();
        parameters.id = id;
        parameters.isDeleted = isDeleted;

        var result = getMessageByIdUseCase.execute(parameters);
        if (result.message.isEmpty()) return ResponseEntity
                .notFound()
                .build();

        var response = modelMapper.map(result.message.get(), MessageResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private AddMessageUseCase addMessageUseCase;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<MessageResponse> addMessage(@RequestBody AddMessageRequest request) throws Exception {
        var parameters = modelMapper.map(request, AddMessageParameters.class);
        var result = addMessageUseCase.execute(parameters);
        var response = modelMapper.map(result.message, MessageResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private UpdateMessageUseCase updateMessageUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<MessageResponse> updateMessage(@PathVariable("id") String id, @RequestBody UpdateMessageRequest request) throws Exception {
        if (!id.equals(request.id)) throw new GenericProblem("Path id and Request Id are not equals!");

        var parameters = modelMapper.map(request, UpdateMessageParameters.class);
        var result = updateMessageUseCase.execute(parameters);
        var response = modelMapper.map(result.message, MessageResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private DeleteMessageUseCase deleteMessageUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<Void> deleteMessage(@PathVariable("id") String id) throws Exception {
        var parameters = new DeleteMessageParameters();
        parameters.id = id;

        deleteMessageUseCase.execute(parameters);
        return ResponseEntity
                .noContent()
                .build();
    }
}
