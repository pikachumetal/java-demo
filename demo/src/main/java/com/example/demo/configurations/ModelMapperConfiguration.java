package com.example.demo.configurations;

import com.example.demo.contracts.categories.add.category.AddCategoryRequestToAddCategoryParametersMapping;
import com.example.demo.contracts.categories.common.CategoryToCategoryResponseMapping;
import com.example.demo.contracts.categories.update.category.UpdateCategoryRequestToUpdateCategoryParametersMapping;
import com.example.demo.contracts.questions.add.question.AddQuestionRequestToAddQuestionParametersMapping;
import com.example.demo.contracts.questions.common.QuestionToQuestionResponseMapping;
import com.example.demo.contracts.questions.update.question.UpdateQuestionRequestToUpdateQuestionParametersMapping;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("unused")
@Configuration
public class ModelMapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.addMappings(new AddCategoryRequestToAddCategoryParametersMapping());
        modelMapper.addMappings(new UpdateCategoryRequestToUpdateCategoryParametersMapping());

        modelMapper.addMappings(new CategoryToCategoryResponseMapping());

        modelMapper.addMappings(new AddQuestionRequestToAddQuestionParametersMapping());
        modelMapper.addMappings(new UpdateQuestionRequestToUpdateQuestionParametersMapping());

        modelMapper.addMappings(new QuestionToQuestionResponseMapping());

        return modelMapper;
    }
}
