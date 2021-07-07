package com.example.demo.contracts.topics.add.topic;

import com.example.demo.use.cases.topics.add.AddTopicParameters;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddTopicParametersMapper {
    @Mapping(source = "description", target = "description")
    @Mapping(source = "email", target = "email")
    AddTopicParameters addTopicRequestToAddTopicParameters(AddTopicRequest source);
}