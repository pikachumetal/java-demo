package com.example.demo.contracts.questions.update.question;

import com.example.demo.use.cases.questions.update.UpdateQuestionParameters;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UpdateQuestionRequestToUpdateQuestionParametersMapping
        extends PropertyMap<UpdateQuestionRequest, UpdateQuestionParameters> {
    @Override
    protected void configure() {
        map().query = source.query;
        map().email = source.email;
        map().answers = source.answers;
    }
}