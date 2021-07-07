package com.example.demo.contracts.questions.add.question;

import manifold.ext.props.rt.api.var;

import java.util.List;

public class AddQuestionRequest {
    @var
    String query;
    @var
    String email;
    @var
    List<String> answers;
    @var
    String topicId;
}
