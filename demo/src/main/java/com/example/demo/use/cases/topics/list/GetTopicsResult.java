package com.example.demo.use.cases.topics.list;

import com.example.demo.domain.Topic;
import manifold.ext.props.rt.api.var;

import java.util.List;

public class GetTopicsResult {
    @var
    List<Topic> topics;

}
