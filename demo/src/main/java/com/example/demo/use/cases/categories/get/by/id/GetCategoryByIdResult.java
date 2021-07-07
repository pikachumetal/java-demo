package com.example.demo.use.cases.categories.get.by.id;

import com.example.demo.domain.Category;
import manifold.ext.props.rt.api.var;

import java.util.Optional;

public class GetCategoryByIdResult {
    @var
    Optional<Category> category;
}
