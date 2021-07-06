package com.example.demo.contracts.messages.common;

import com.example.demo.contracts.authors.common.AuthorResponse;
import manifold.ext.props.rt.api.var;

import java.util.List;

public class MessagesResponse {
    @var
    List<MessageResponse> messages;
}
