package com.example.demo.use.cases.messages.get.by.id;

import com.example.demo.domain.Author;
import com.example.demo.domain.Message;
import manifold.ext.props.rt.api.var;

import java.util.Optional;

public class GetMessageByIdResult {
    @var
    Optional<Message> message;
}
