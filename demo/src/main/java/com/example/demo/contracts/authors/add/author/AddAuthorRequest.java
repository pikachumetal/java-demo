package com.example.demo.contracts.authors.add.author;

import manifold.ext.props.rt.api.var;

public class AddAuthorRequest {
    @var
    String firstName;
    @var
    String lastName;
    @var
    String email;
}
