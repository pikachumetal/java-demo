package com.example.demo.configurations;

import com.example.demo.contracts.questions.common.QuestionResponse;
import com.example.demo.contracts.topics.add.topic.AddTopicRequestToAddTopicParametersMapping;
import com.example.demo.contracts.topics.common.TopicToTopicResponseMapping;
import com.example.demo.contracts.topics.update.topic.UpdateTopicRequestToUpdateTopicParametersMapping;
import com.example.demo.contracts.questions.add.question.AddQuestionRequestToAddQuestionParametersMapping;
import com.example.demo.contracts.questions.common.QuestionToQuestionResponseMapping;
import com.example.demo.contracts.questions.update.question.UpdateQuestionRequestToUpdateQuestionParametersMapping;
import com.example.demo.domain.Question;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("unused")
@Configuration
public class ModelMapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.addMappings(new AddTopicRequestToAddTopicParametersMapping());
        modelMapper.addMappings(new UpdateTopicRequestToUpdateTopicParametersMapping());

        modelMapper.addMappings(new TopicToTopicResponseMapping());

        modelMapper.addMappings(new AddQuestionRequestToAddQuestionParametersMapping());
        modelMapper.addMappings(new UpdateQuestionRequestToUpdateQuestionParametersMapping());

        modelMapper.addMappings(new QuestionToQuestionResponseMapping());

        modelMapper.typeMap(Question.class, QuestionResponse.class)
                .setConverter(new Converter<Question, QuestionResponse>() {
            @Override
            public QuestionResponse convert(MappingContext<Question, QuestionResponse> mappingContext) {
                return modelMapper.map(mappingContext.getSource(), QuestionResponse.class);
            }
        });

        return modelMapper;
    }
}
