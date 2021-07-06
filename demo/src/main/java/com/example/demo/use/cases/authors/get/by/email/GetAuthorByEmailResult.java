package com.example.demo.use.cases.authors.get.by.email;

import com.example.demo.domain.Author;
import manifold.ext.props.rt.api.var;

import java.util.Optional;

public class GetAuthorByEmailResult {
    @var
    Optional<Author> author;
}
