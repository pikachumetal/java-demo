package com.example.demo.contracts.topics.update.topic;

import manifold.ext.props.rt.api.var;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UpdateTopicRequest {
    @var
    @Size(max = 256)
    @NotEmpty
    String description;

    @var
    @Size(min = 3, max = 128)
    @NotEmpty
    @Email
    String email;
}
