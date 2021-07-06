package com.example.demo.use.cases.authors.get.by.id;

import com.example.demo.domain.Author;
import manifold.ext.props.rt.api.var;

import java.util.Optional;

public class GetAuthorByIdResult {
    @var
    Optional<Author> author;
}
