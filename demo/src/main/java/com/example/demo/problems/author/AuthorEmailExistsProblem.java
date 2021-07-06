package com.example.demo.problems.author;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class AuthorEmailExistsProblem extends AbstractThrowableProblem {
    public AuthorEmailExistsProblem(String email) {
        super(null,
                "Bad request",
                Status.BAD_REQUEST,
                String.format("Author with email '%s' already exist", email));
    }
}

