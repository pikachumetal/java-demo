package com.example.demo.use.cases.authors.list;

import com.example.demo.domain.Author;
import manifold.ext.props.rt.api.var;

import java.util.List;
import java.util.Set;

public class GetAuthorsResult {
    @var
    List<Author> authors;

}
