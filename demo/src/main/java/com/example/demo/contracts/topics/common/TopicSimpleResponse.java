package com.example.demo.contracts.topics.common;

import com.example.demo.contracts.questions.common.QuestionResponse;
import manifold.ext.props.rt.api.var;

import java.time.Instant;
import java.util.List;

public class TopicSimpleResponse {
    @var
    String id;
    @var
    String description;
    @var
    String email;
    @var
    boolean active;
    @var
    Instant created;
    @var
    Instant updated;
}
