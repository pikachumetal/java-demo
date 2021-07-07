package com.example.demo.use.cases.questions.list;

import com.example.demo.domain.Question;
import manifold.ext.props.rt.api.var;

import java.util.List;

public class GetQuestionsResult {
    @var
    List<Question> questions;
}
