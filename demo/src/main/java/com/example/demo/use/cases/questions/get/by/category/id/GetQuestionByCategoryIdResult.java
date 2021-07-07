package com.example.demo.use.cases.questions.get.by.category.id;

import com.example.demo.domain.Question;
import manifold.ext.props.rt.api.var;

import java.util.List;

public class GetQuestionByCategoryIdResult {
    @var
    List<Question> questions;
}
