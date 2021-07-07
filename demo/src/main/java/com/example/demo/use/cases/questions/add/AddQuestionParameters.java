package com.example.demo.use.cases.questions.add;

import manifold.ext.props.rt.api.var;

import java.util.List;

public class AddQuestionParameters {
    @var
    String query;
    @var
    String email;
    @var
    List<String> answers;
    @var
    String topicId;
}
