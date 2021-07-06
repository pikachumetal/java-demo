package com.example.demo.problems.author;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class AuthorNonExistsProblem extends AbstractThrowableProblem {
    public AuthorNonExistsProblem(String id) {
        super(null,
                "Bad request",
                Status.BAD_REQUEST,
                String.format("Author with id '%s' non exist", id));
    }
}

