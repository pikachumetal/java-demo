package com.example.demo.contracts.messages.common;

import com.example.demo.contracts.authors.common.AuthorResponse;
import com.example.demo.domain.Author;
import com.example.demo.domain.Message;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class MessageToMessageResponseMapping extends PropertyMap<Message, MessageResponse> {
    @Override
    protected void configure() {
        map().id = source.id;
        map().title = source.title;
        map().message = source.message;
        map(source.author, destination.author);
    }
}