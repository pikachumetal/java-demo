package com.example.demo.contracts.topics.update.topic;

import com.example.demo.contracts.questions.update.question.UpdateQuestionRequest;
import com.example.demo.use.cases.questions.update.UpdateQuestionParameters;
import com.example.demo.use.cases.topics.update.UpdateTopicParameters;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateTopicParametersMapper {
    @Mapping(source = "topicId", target = "id")
    @Mapping(source = "source.description", target = "description")
    @Mapping(source = "source.email", target = "email")
    UpdateTopicParameters updateTopicRequestToUpdateTopicParameters(UpdateTopicRequest source, String topicId);
}