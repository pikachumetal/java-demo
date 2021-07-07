package com.example.demo.problems.category;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CategoryDescriptionExistsProblem
        extends AbstractThrowableProblem {
    public CategoryDescriptionExistsProblem(String description) {
        super(null,
                "Bad request",
                Status.BAD_REQUEST,
                String.format("Category with description '%s' already exist", description));
    }
}

