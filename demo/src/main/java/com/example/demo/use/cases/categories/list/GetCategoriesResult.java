package com.example.demo.use.cases.categories.list;

import com.example.demo.domain.Category;
import manifold.ext.props.rt.api.var;

import java.util.List;

public class GetCategoriesResult {
    @var
    List<Category> categories;

}
