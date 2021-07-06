package com.example.demo.problems;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class GenericProblem extends AbstractThrowableProblem {
    public GenericProblem(Exception e) {
        super(null,
                "Interval server error",
                Status.INTERNAL_SERVER_ERROR,
                "Interval server error: %s".formatted(e.message));
    }

    public GenericProblem(String message) {
        super(null,
                "Interval server error",
                Status.INTERNAL_SERVER_ERROR,
                "Interval server error: %s".formatted(message));
    }

    public GenericProblem() {
        super(null,
                "Interval server error",
                Status.INTERNAL_SERVER_ERROR,
                "Interval server error");
    }
}