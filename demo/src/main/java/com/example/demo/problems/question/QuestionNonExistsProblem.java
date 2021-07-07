package com.example.demo.problems.question;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class QuestionNonExistsProblem
        extends AbstractThrowableProblem {
    public QuestionNonExistsProblem(String id) {
        super(null,
                "Bad request",
                Status.BAD_REQUEST,
                String.format("Question with id '%s' non exist", id));
    }
}

