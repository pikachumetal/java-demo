package com.example.demo.contracts.topics.add.topic;

import manifold.ext.props.rt.api.var;

import javax.validation.constraints.*;

public class AddTopicRequest {
    @var
    @Size(min = 1, max = 256, message = "Description cannot be longer than {max} characters")
    @NotBlank(message = "Description is a required field")
    String description;

    @var
    @Size(min = 3, max = 128, message = "Email cannot be longer than {max} characters")
    @NotBlank(message = "Email is a required field")
    @Email(message = "${validatedValue} is not a valid email")
    String email;
}
