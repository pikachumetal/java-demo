package com.example.demo.contracts.topics.common;

import com.example.demo.contracts.questions.common.QuestionResponse;
import com.example.demo.domain.Question;
import com.example.demo.domain.Topic;
import org.modelmapper.AbstractConverter;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicToTopicResponseMapping
        extends PropertyMap<Topic, TopicResponse> {
    @Override
    protected void configure() {
        map().id = source.id;
        map().email = source.email;
        map().description = source.description;
        map().active = source.active;
        map().created = source.created;
        map().updated = source.updated;
    }
}