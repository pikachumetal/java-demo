package com.example.demo.use.cases.messages.list;

import com.example.demo.domain.Message;
import manifold.ext.props.rt.api.var;

import java.util.List;

public class GetMessagesResult {
    @var
    List<Message> messages;
}
