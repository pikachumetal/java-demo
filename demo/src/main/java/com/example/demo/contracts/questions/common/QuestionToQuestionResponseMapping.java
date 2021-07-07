package com.example.demo.contracts.questions.common;

import com.example.demo.domain.Question;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class QuestionToQuestionResponseMapping
        extends PropertyMap<Question, QuestionResponse> {
    @Override
    protected void configure() {
        map().id = source.id;
        map().query = source.query;
        map().active = source.active;
        map().email = source.email;
        map().answers = source.answers;
        map(source.topic, destination.topic);
        map().created = source.created;
        map().updated = source.updated;
    }
}