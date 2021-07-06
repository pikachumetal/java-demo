package com.example.demo.contracts.authors.common;

import manifold.ext.props.rt.api.var;

import java.util.List;

public class AuthorsResponse {
    @var
    List<AuthorResponse> authors;
}
