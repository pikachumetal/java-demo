package com.example.demo.contracts.questions.add.question;

import manifold.ext.props.rt.api.var;

public class AddQuestionRequest {
    @var
    String query;
    @var
    String email;
    @var
    String categoryId;
}
