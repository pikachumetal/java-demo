package com.example.demo.contracts.questions.common;

import com.example.demo.contracts.topics.common.TopicSimpleResponse;
import manifold.ext.props.rt.api.var;

import java.time.Instant;
import java.util.List;

public class QuestionSimpleResponse {
    @var
    String id;
    @var
    String query;
    @var
    String email;
    @var
    List<String> answers;
    @var
    boolean active;
    @var
    Instant created;
    @var
    Instant updated;
}
