package com.example.demo.problems.question;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class QuestionQueryExistsProblem
        extends AbstractThrowableProblem {
    public QuestionQueryExistsProblem(String query) {
        super(null,
                "Bad request",
                Status.BAD_REQUEST,
                String.format("Question with query '%s' already exist", query));
    }
}

