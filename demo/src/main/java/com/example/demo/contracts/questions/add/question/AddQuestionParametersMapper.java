package com.example.demo.contracts.questions.add.question;

import com.example.demo.use.cases.questions.add.AddQuestionParameters;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddQuestionParametersMapper {
    @Mapping(source = "source.query", target = "query")
    @Mapping(source = "source.email", target = "email")
    @Mapping(source = "source.answers", target = "answers")
    @Mapping(source = "topicId", target = "topicId")
    AddQuestionParameters addQuestionRequestToAddQuestionParameters(AddQuestionRequest source, String topicId);
}