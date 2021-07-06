package com.example.demo.contracts.messages.add.message;

import com.example.demo.use.cases.messages.add.AddMessageParameters;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AddMessageRequestToAddMessageParametersMapping extends PropertyMap<AddMessageRequest, AddMessageParameters> {
    @Override
    protected void configure() {
        map().title = source.title;
        map().message = source.message;
        map().authorId = source.authorId;
    }
}