package com.example.demo.contracts.questions.common;

import com.example.demo.contracts.categories.common.CategoryResponse;
import manifold.ext.props.rt.api.var;

import java.time.Instant;

public class QuestionResponse {
    @var
    String id;
    @var
    String query;
    @var
    String email;
    @var
    boolean active;
    @var
    CategoryResponse category;
    @var
    Instant created;
    @var
    Instant updated;
}
