package com.example.demo.contracts.questions.common;

import com.example.demo.domain.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface QuestionResponseMapper {
    List<QuestionResponse> questionsToQuestionResponses(List<Question> questions);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "query", target = "query")
    @Mapping(source = "answers", target = "answers")
    @Mapping(source = "active", target = "active")
    @Mapping(source = "created", target = "created")
    @Mapping(source = "updated", target = "updated")
    @Mapping(source = "topic.id", target = "topic.id")
    @Mapping(source = "topic.description", target = "topic.description")
    @Mapping(source = "topic.email", target = "topic.email")
    @Mapping(source = "topic.active", target = "topic.active")
    @Mapping(source = "topic.created", target = "topic.created")
    @Mapping(source = "topic.updated", target = "topic.updated")
    QuestionResponse questionToQuestionResponse(Question source);
}