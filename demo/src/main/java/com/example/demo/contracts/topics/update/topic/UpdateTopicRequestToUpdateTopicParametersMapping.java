package com.example.demo.contracts.topics.update.topic;

import com.example.demo.use.cases.topics.update.UpdateTopicParameters;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UpdateTopicRequestToUpdateTopicParametersMapping
        extends PropertyMap<UpdateTopicRequest, UpdateTopicParameters> {
    @Override
    protected void configure() {
        map().description = source.description;
        map().email = source.email;
    }
}