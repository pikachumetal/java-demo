package com.example.demo.use.cases.messages.add;

import com.example.demo.domain.Message;
import com.example.demo.use.cases.messages.add.AddMessageParameters;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AddMessageParametersToMessageMapping extends PropertyMap<AddMessageParameters, Message> {
    @Override
    protected void configure() {
        map().title = source.title;
        map().message = source.message;
    }
}