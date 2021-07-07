package com.example.demo.contracts.questions.add.question;

import com.example.demo.use.cases.questions.add.AddQuestionParameters;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AddQuestionRequestToAddQuestionParametersMapping
        extends PropertyMap<AddQuestionRequest, AddQuestionParameters> {
    @Override
    protected void configure() {
        map().query = source.query;
        map().email = source.email;
        map().categoryId = source.categoryId;
    }
}