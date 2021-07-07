package com.example.demo.use.cases.topics.get.by.id;

import manifold.ext.props.rt.api.var;

public class GetTopicByIdParameters {
    @var
    String id;
    @var
    boolean showDeleted;
}
