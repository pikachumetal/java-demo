package com.example.demo.use.cases.categories.get.by.id;

import manifold.ext.props.rt.api.var;

public class GetCategoryByIdParameters {
    @var
    String id;
    @var
    boolean showDeleted;
}
