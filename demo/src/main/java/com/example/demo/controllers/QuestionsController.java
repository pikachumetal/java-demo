package com.example.demo.controllers;

import com.example.demo.contracts.questions.add.question.AddQuestionParametersMapper;
import com.example.demo.contracts.questions.add.question.AddQuestionRequest;
import com.example.demo.contracts.questions.common.QuestionResponse;
import com.example.demo.contracts.questions.common.QuestionResponseMapper;
import com.example.demo.contracts.questions.common.QuestionsResponse;
import com.example.demo.contracts.questions.update.question.UpdateQuestionParametersMapper;
import com.example.demo.contracts.questions.update.question.UpdateQuestionRequest;
import com.example.demo.use.cases.questions.add.AddQuestionUseCase;
import com.example.demo.use.cases.questions.delete.DeleteQuestionParameters;
import com.example.demo.use.cases.questions.delete.DeleteQuestionUseCase;
import com.example.demo.use.cases.questions.get.by.id.GetQuestionByIdParameters;
import com.example.demo.use.cases.questions.get.by.id.GetQuestionByIdUseCase;
import com.example.demo.use.cases.questions.get.by.topic.id.GetQuestionsByTopicIdParameters;
import com.example.demo.use.cases.questions.get.by.topic.id.GetQuestionsByTopicIdUseCase;
import com.example.demo.use.cases.questions.list.GetQuestionsParameters;
import com.example.demo.use.cases.questions.list.GetQuestionsUseCase;
import com.example.demo.use.cases.questions.toggle.ToggleQuestionParameters;
import com.example.demo.use.cases.questions.toggle.ToggleQuestionUseCase;
import com.example.demo.use.cases.questions.update.UpdateQuestionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class QuestionsController {

    @Autowired
    private GetQuestionsUseCase getQuestionsUseCase;

    @Autowired
    private QuestionResponseMapper questionResponseMapper;

    @RequestMapping(value = "/questions",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<QuestionsResponse> getQuestions() throws Exception {
        var parameters = new GetQuestionsParameters();

        var result = getQuestionsUseCase.execute(parameters);

        var response = new QuestionsResponse();
        response.questions = result.questions.stream()
                .map(o -> questionResponseMapper.questionToQuestionResponse(o))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private GetQuestionsByTopicIdUseCase getQuestionsByTopicIdUseCase;

    @RequestMapping(value = "/topics/{id}/questions",
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
                .map(o -> questionResponseMapper.questionToQuestionResponse(o))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private GetQuestionByIdUseCase getQuestionByIdUseCase;

    @RequestMapping(value = "/questions/{id}",
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
        if (result.question.isEmpty()) return ResponseEntity
                .notFound()
                .build();

        var response = questionResponseMapper
                .questionToQuestionResponse(result.question.get());
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private AddQuestionUseCase addQuestionUseCase;

    @Autowired
    private AddQuestionParametersMapper addQuestionParameterMapper;

    @RequestMapping(value = "/topics/{id}/questions",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<QuestionResponse> addQuestion(
            @PathVariable("id") String topicId,
            @RequestBody AddQuestionRequest request
    ) throws Exception {
        var parameters = addQuestionParameterMapper
                .addQuestionRequestToAddQuestionParameters(request, topicId);

        var result = addQuestionUseCase.execute(parameters);
        var response = questionResponseMapper
                .questionToQuestionResponse(result.question);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private UpdateQuestionUseCase updateQuestionUseCase;

    @Autowired
    private UpdateQuestionParametersMapper updateQuestionParametersMapper;

    @RequestMapping(value = "/questions/{id}",
            method = RequestMethod.PUT,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<QuestionResponse> updateQuestion(
            @PathVariable("id") String id,
            @RequestBody UpdateQuestionRequest request
    ) throws Exception {
        var parameters = updateQuestionParametersMapper
                .updateQuestionRequestToUpdateQuestionParameters(request, id);

        var result = updateQuestionUseCase.execute(parameters);
        var response = questionResponseMapper
                .questionToQuestionResponse(result.question);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private ToggleQuestionUseCase toggleQuestionUseCase;

    @RequestMapping(value = "/questions/{id}/toggle",
            method = RequestMethod.PUT,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<QuestionResponse> toggleQuestion(
            @PathVariable("id") String id
    ) throws Exception {
        var parameters = new ToggleQuestionParameters();
        parameters.id = id;

        var result = toggleQuestionUseCase.execute(parameters);
        var response = questionResponseMapper
                .questionToQuestionResponse(result.question);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private DeleteQuestionUseCase deleteQuestionUseCase;

    @RequestMapping(value = "/questions/{id}",
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
