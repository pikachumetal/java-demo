package com.example.demo.use.cases.authors.get.by.id;

import manifold.ext.props.rt.api.var;

public class GetAuthorByIdParameters {
    @var
    String id;
    @var
    boolean isDeleted;
}
