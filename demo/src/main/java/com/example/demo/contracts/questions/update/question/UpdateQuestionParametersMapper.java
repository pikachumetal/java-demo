package com.example.demo.contracts.questions.update.question;

import com.example.demo.use.cases.questions.update.UpdateQuestionParameters;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateQuestionParametersMapper {
    @Mapping(source = "questionId", target = "id")
    @Mapping(source = "source.email", target = "email")
    @Mapping(source = "source.query", target = "query")
    @Mapping(source = "source.answers", target = "answers")
    UpdateQuestionParameters updateQuestionRequestToUpdateQuestionParameters(UpdateQuestionRequest source, String questionId);
}