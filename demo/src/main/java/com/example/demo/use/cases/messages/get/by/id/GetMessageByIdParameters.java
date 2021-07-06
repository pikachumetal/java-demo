package com.example.demo.use.cases.messages.get.by.id;

import manifold.ext.props.rt.api.var;

public class GetMessageByIdParameters {
    @var
    String id;
    @var
    boolean isDeleted;
}
