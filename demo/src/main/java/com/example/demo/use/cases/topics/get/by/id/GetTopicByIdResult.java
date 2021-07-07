package com.example.demo.use.cases.topics.get.by.id;

import com.example.demo.domain.Topic;
import manifold.ext.props.rt.api.var;

import java.util.Optional;

public class GetTopicByIdResult {
    @var
    Optional<Topic> topic;
}
