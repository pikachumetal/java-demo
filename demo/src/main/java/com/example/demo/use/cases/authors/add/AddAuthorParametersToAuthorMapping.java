package com.example.demo.use.cases.authors.add;

import com.example.demo.domain.Author;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddAuthorParametersToAuthorMapping extends PropertyMap<AddAuthorParameters, Author> {
    @Override
    protected void configure() {
        map().id = null;
        map().email = source.email;
        map().firstName = source.firstName;
        map().lastName = source.lastName;
    }
}