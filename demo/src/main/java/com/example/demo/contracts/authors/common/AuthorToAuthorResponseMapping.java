package com.example.demo.contracts.authors.common;

import com.example.demo.domain.Author;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AuthorToAuthorResponseMapping extends PropertyMap<Author, AuthorResponse> {
    @Override
    protected void configure() {
        map().id = source.id;
        map().email = source.email;
        map().firstName = source.firstName;
        map().lastName = source.lastName;
    }
}