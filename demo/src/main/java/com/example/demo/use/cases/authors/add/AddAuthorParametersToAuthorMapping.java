package com.example.demo.use.cases.authors.add;

import com.example.demo.domain.Author;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AddAuthorParametersToAuthorMapping extends PropertyMap<AddAuthorParameters, Author> {
    @Override
    protected void configure() {
        map().email = source.email;
        map().firstName = source.firstName;
        map().lastName = source.lastName;
    }
}