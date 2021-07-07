package com.example.demo.contracts.questions.common;

import com.example.demo.contracts.topics.common.TopicResponse;
import manifold.ext.props.rt.api.var;

import java.time.Instant;
import java.util.List;

public class QuestionResponse {
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
    TopicResponse topic;
    @var
    Instant created;
    @var
    Instant updated;
}
