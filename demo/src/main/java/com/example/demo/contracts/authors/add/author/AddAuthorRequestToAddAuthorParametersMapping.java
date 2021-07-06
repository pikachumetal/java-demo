package com.example.demo.contracts.authors.add.author;

import com.example.demo.use.cases.authors.add.AddAuthorParameters;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AddAuthorRequestToAddAuthorParametersMapping extends PropertyMap<AddAuthorRequest, AddAuthorParameters> {
    @Override
    protected void configure() {
        map().email = source.email;
        map().firstName = source.firstName;
        map().lastName = source.lastName;
    }
}