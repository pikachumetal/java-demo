package com.example.demo.configurations;

import com.example.demo.contracts.authors.add.author.AddAuthorRequestToAddAuthorParametersMapping;
import com.example.demo.contracts.authors.common.AuthorToAuthorResponseMapping;
import com.example.demo.contracts.authors.update.author.UpdateAuthorRequestToUpdateAuthorParametersMapping;
import com.example.demo.contracts.messages.add.message.AddMessageRequestToAddMessageParametersMapping;
import com.example.demo.contracts.messages.common.MessageToMessageResponseMapping;
import com.example.demo.contracts.messages.update.message.UpdateMessageRequestToUpdateMessageParametersMapping;
import com.example.demo.use.cases.authors.add.AddAuthorParametersToAuthorMapping;
import com.example.demo.use.cases.authors.update.UpdateAuthorParametersToAuthorMapping;
import com.example.demo.use.cases.messages.add.AddMessageParametersToMessageMapping;
import com.example.demo.use.cases.messages.update.UpdateMessageParametersToMessageMapping;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("unused")
@Configuration
public class ModelMapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.addMappings(new AddAuthorRequestToAddAuthorParametersMapping());
        modelMapper.addMappings(new AddAuthorParametersToAuthorMapping());

        modelMapper.addMappings(new UpdateAuthorRequestToUpdateAuthorParametersMapping());
        modelMapper.addMappings(new UpdateAuthorParametersToAuthorMapping());

        modelMapper.addMappings(new AuthorToAuthorResponseMapping());

        modelMapper.addMappings(new AddMessageRequestToAddMessageParametersMapping());
        modelMapper.addMappings(new AddMessageParametersToMessageMapping());

        modelMapper.addMappings(new UpdateMessageRequestToUpdateMessageParametersMapping());
        modelMapper.addMappings(new UpdateMessageParametersToMessageMapping());

        modelMapper.addMappings(new MessageToMessageResponseMapping());

        return modelMapper;
    }
}
