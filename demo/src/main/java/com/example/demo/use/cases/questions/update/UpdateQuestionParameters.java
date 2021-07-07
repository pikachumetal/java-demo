package com.example.demo.use.cases.questions.update;

import manifold.ext.props.rt.api.var;

import java.util.List;

public class UpdateQuestionParameters {
    @var
    String id;
    @var
    String query;
    @var
    String email;
    @var
    List<String> answers;
}
