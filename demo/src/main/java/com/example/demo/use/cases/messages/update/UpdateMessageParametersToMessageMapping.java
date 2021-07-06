package com.example.demo.use.cases.messages.update;

import com.example.demo.domain.Message;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UpdateMessageParametersToMessageMapping extends PropertyMap<UpdateMessageParameters, Message> {
    @Override
    protected void configure() {
        map().setId(source.id);
        map().title = source.title;
        map().message = source.message;
    }
}