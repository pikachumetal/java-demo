package com.example.demo.contracts.messages.update.message;

import com.example.demo.use.cases.messages.update.UpdateMessageParameters;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UpdateMessageRequestToUpdateMessageParametersMapping extends PropertyMap<UpdateMessageRequest, UpdateMessageParameters> {
    @Override
    protected void configure() {
        map().id = source.id;
        map().title = source.title;
        map().message = source.message;
    }
}