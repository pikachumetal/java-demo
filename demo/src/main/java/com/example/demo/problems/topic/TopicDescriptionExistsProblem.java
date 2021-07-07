package com.example.demo.problems.topic;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class TopicDescriptionExistsProblem
        extends AbstractThrowableProblem {
    public TopicDescriptionExistsProblem(String description) {
        super(null,
                "Bad request",
                Status.BAD_REQUEST,
                String.format("Topic with description '%s' already exist", description));
    }
}

