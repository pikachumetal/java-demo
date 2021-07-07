package com.example.demo.use.cases.questions.get.by.id;

import manifold.ext.props.rt.api.var;

public class GetQuestionByIdParameters {
    @var
    String id;
    @var
    boolean showDeleted;
}
