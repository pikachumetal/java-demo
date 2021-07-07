package com.example.demo.contracts.topics.add.topic;

import com.example.demo.use.cases.topics.add.AddTopicParameters;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AddTopicRequestToAddTopicParametersMapping
        extends PropertyMap<AddTopicRequest, AddTopicParameters> {
    @Override
    protected void configure() {
        map().description = source.description;
        map().email = source.email;
    }
}