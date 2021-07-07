package com.example.demo.problems.category;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CategoryNonExistsProblem
        extends AbstractThrowableProblem {
    public CategoryNonExistsProblem(String id) {
        super(null,
                "Bad request",
                Status.BAD_REQUEST,
                String.format("Category with id '%s' non exist", id));
    }
}

