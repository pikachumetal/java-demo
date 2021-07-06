package com.example.demo.use.cases.messages.get.by.author.id;

import com.example.demo.domain.Message;
import manifold.ext.props.rt.api.var;

import java.util.List;

public class GetMessagesByAuthorIdResult {
    @var
    List<Message> messages;
}
