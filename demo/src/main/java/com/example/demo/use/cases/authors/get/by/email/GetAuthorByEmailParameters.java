package com.example.demo.use.cases.authors.get.by.email;

import manifold.ext.props.rt.api.var;

public class GetAuthorByEmailParameters {
    @var
    String email;
    @var
    boolean isDeleted;
}

