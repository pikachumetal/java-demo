package com.example.demo.controllers;

import com.example.demo.contracts.questions.add.question.AddQuestionRequest;
import com.example.demo.contracts.questions.common.QuestionResponse;
import com.example.demo.contracts.questions.common.QuestionsResponse;
import com.example.demo.contracts.questions.update.question.UpdateQuestionRequest;
import com.example.demo.use.cases.questions.add.AddQuestionParameters;
import com.example.demo.use.cases.questions.add.AddQuestionUseCase;
import com.example.demo.use.cases.questions.delete.DeleteQuestionParameters;
import com.example.demo.use.cases.questions.delete.DeleteQuestionUseCase;
import com.example.demo.use.cases.questions.get.by.topic.id.GetQuestionsByTopicIdParameters;
import com.example.demo.use.cases.questions.get.by.topic.id.GetQuestionsByTopicIdUseCase;
import com.example.demo.use.cases.questions.get.by.id.GetQuestionByIdParameters;
import com.example.demo.use.cases.questions.get.by.id.GetQuestionByIdUseCase;
import com.example.demo.use.cases.questions.list.GetQuestionsParameters;
import com.example.demo.use.cases.questions.list.GetQuestionsUseCase;
import com.example.demo.use.cases.questions.update.UpdateQuestionParameters;
import com.example.demo.use.cases.questions.update.UpdateQuestionUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/questions")
@SuppressWarnings("unused")
public class QuestionsController {

    @SuppressWarnings("unused")
    @Autowired
    private ModelMapper modelMapper;

    @SuppressWarnings("unused")
    @Autowired
    private GetQuestionsUseCase getQuestionsUseCase;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<QuestionsResponse> getQuestions() throws Exception {
        var parameters = new GetQuestionsParameters();

        var result = getQuestionsUseCase.execute(parameters);

        var response = new QuestionsResponse();
        response.questions = result.questions.stream()
                .map(o -> modelMapper.map(o, QuestionResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private GetQuestionsByTopicIdUseCase getQuestionsByTopicIdUseCase;

    @RequestMapping(value = "/topics/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<QuestionsResponse> getQuestionsByTopicId(
            @PathVariable("id") String id
    ) throws Exception {
        var parameters = new GetQuestionsByTopicIdParameters();
        parameters.id = id;

        var result = getQuestionsByTopicIdUseCase.execute(parameters);

        var response = new QuestionsResponse();
        response.questions = result.questions.stream()
                .map(o -> modelMapper.map(o, QuestionResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private GetQuestionByIdUseCase getQuestionByIdUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<QuestionResponse> getQuestionById(
            @PathVariable("id") String id,
            @RequestParam(value = "showDeleted", required = false, defaultValue = "false") boolean showDeleted
    ) throws Exception {
        var parameters = new GetQuestionByIdParameters();
        parameters.id = id;
        parameters.showDeleted = showDeleted;

        var result = getQuestionByIdUseCase.execute(parameters);
        if (result.message.isEmpty()) return ResponseEntity
                .notFound()
                .build();

        var response = modelMapper.map(result.message.get(), QuestionResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private AddQuestionUseCase addQuestionUseCase;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<QuestionResponse> addQuestion(
            @RequestBody AddQuestionRequest request
    ) throws Exception {
        var parameters = modelMapper.map(request, AddQuestionParameters.class);
        var result = addQuestionUseCase.execute(parameters);
        var response = modelMapper.map(result.question, QuestionResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private UpdateQuestionUseCase updateQuestionUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<QuestionResponse> updateQuestion(
            @PathVariable("id") String id,
            @RequestBody UpdateQuestionRequest request
    ) throws Exception {
        var parameters = modelMapper.map(request, UpdateQuestionParameters.class);
        parameters.id = id;

        var result = updateQuestionUseCase.execute(parameters);
        var response = modelMapper.map(result.question, QuestionResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private DeleteQuestionUseCase deleteQuestionUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable("id") String id
    ) throws Exception {
        var parameters = new DeleteQuestionParameters();
        parameters.id = id;

        deleteQuestionUseCase.execute(parameters);
        return ResponseEntity
                .noContent()
                .build();
    }
}
