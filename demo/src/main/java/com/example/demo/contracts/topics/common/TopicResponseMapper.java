package com.example.demo.contracts.topics.common;

import com.example.demo.contracts.questions.common.QuestionResponseMapper;
import com.example.demo.contracts.questions.common.QuestionSimpleResponseMapper;
import com.example.demo.domain.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = QuestionSimpleResponseMapper.class, componentModel = "spring")
public interface TopicResponseMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "questions", target = "questions")
    @Mapping(source = "active", target = "active")
    @Mapping(source = "created", target = "created")
    @Mapping(source = "updated", target = "updated")
    TopicResponse topicToTopicResponse(Topic source);
}