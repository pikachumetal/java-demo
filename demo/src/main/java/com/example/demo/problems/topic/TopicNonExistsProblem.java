package com.example.demo.problems.topic;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class TopicNonExistsProblem
        extends AbstractThrowableProblem {
    public TopicNonExistsProblem(String id) {
        super(null,
                "Bad request",
                Status.BAD_REQUEST,
                String.format("Topic with id '%s' non exist", id));
    }
}

