package com.example.demo.contracts.messages.common;

import com.example.demo.contracts.authors.common.AuthorResponse;
import manifold.ext.props.rt.api.var;

public class MessageResponse {
    @var
    String id;
    @var
    String title;
    @var
    String message;
    @var
    AuthorResponse author;
}
