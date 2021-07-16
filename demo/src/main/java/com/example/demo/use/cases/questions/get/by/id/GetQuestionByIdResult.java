package com.example.demo.use.cases.questions.get.by.id;

import com.example.demo.domain.Question;
import manifold.ext.props.rt.api.var;

import java.util.Optional;

public class GetQuestionByIdResult {
    @var
    Optional<Question> question;
}
