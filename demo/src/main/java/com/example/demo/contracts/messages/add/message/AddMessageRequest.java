package com.example.demo.contracts.messages.add.message;

import manifold.ext.props.rt.api.var;

public class AddMessageRequest {
    @var
    String title;
    @var
    String message;
    @var
    String authorId;
}
