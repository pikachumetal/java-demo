package com.example.demo.use.cases.authors.update;

import com.example.demo.domain.Author;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UpdateAuthorParametersToAuthorMapping extends PropertyMap<UpdateAuthorParameters, Author> {
    @Override
    protected void configure() {
        map().setId(source.id);
        map().email = source.email;
        map().firstName = source.firstName;
        map().lastName = source.lastName;
    }
}