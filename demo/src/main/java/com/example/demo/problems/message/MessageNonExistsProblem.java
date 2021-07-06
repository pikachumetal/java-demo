package com.example.demo.problems.message;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class MessageNonExistsProblem extends AbstractThrowableProblem {
    public MessageNonExistsProblem(String id) {
        super(null,
                "Bad request",
                Status.BAD_REQUEST,
                String.format("Message with id '%s' non exist", id));
    }
}

