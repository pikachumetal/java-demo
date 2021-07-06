package com.example.demo.contracts.authors.update.author;

import manifold.ext.props.rt.api.var;

public class UpdateAuthorRequest {
    @var
    String id;
    @var
    String firstName;
    @var
    String lastName;
    @var
    String email;
}
