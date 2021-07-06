package com.example.demo.contracts.authors.update.author;

import com.example.demo.use.cases.authors.update.UpdateAuthorParameters;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UpdateAuthorRequestToUpdateAuthorParametersMapping extends PropertyMap<UpdateAuthorRequest, UpdateAuthorParameters> {
    @Override
    protected void configure() {
        map().id = source.id;
        map().email = source.email;
        map().firstName = source.firstName;
        map().lastName = source.lastName;
    }
}