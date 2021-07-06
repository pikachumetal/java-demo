package com.example.demo.contracts.messages.update.message;

import manifold.ext.props.rt.api.var;

public class UpdateMessageRequest {
    @var
    String id;
    @var
    String title;
    @var
    String message;
}
