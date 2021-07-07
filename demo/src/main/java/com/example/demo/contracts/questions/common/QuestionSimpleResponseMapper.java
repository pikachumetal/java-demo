package com.example.demo.contracts.questions.common;

import com.example.demo.domain.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionSimpleResponseMapper {
    List<QuestionSimpleResponse> questionsToQuestionSimpleResponses(List<Question> questions);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "query", target = "query")
    @Mapping(source = "answers", target = "answers")
    @Mapping(source = "active", target = "active")
    @Mapping(source = "created", target = "created")
    @Mapping(source = "updated", target = "updated")
    QuestionResponse questionToQuestionSimpleResponse(Question source);
}