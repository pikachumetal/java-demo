package com.example.demo.controllers;

import com.example.demo.contracts.topics.add.topic.AddTopicParametersMapper;
import com.example.demo.contracts.topics.add.topic.AddTopicRequest;
import com.example.demo.contracts.topics.common.TopicResponse;
import com.example.demo.contracts.topics.common.TopicResponseMapper;
import com.example.demo.contracts.topics.common.TopicsResponse;
import com.example.demo.contracts.topics.update.topic.UpdateTopicParametersMapper;
import com.example.demo.contracts.topics.update.topic.UpdateTopicRequest;
import com.example.demo.use.cases.topics.add.AddTopicUseCase;
import com.example.demo.use.cases.topics.delete.DeleteTopicParameters;
import com.example.demo.use.cases.topics.delete.DeleteTopicUseCase;
import com.example.demo.use.cases.topics.get.by.id.GetTopicByIdParameters;
import com.example.demo.use.cases.topics.get.by.id.GetTopicByIdUseCase;
import com.example.demo.use.cases.topics.list.GetTopicsParameters;
import com.example.demo.use.cases.topics.list.GetTopicsUseCase;
import com.example.demo.use.cases.topics.toggle.ToggleTopicParameters;
import com.example.demo.use.cases.topics.toggle.ToggleTopicUseCase;
import com.example.demo.use.cases.topics.update.UpdateTopicUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/topics")
public class TopicsController {

    @Autowired
    private GetTopicsUseCase getTopicsUseCase;

    @Autowired
    private TopicResponseMapper topicResponseMapper;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<TopicsResponse> getTopics() throws Exception {
        var parameters = new GetTopicsParameters();

        var result = getTopicsUseCase.execute(parameters);

        var response = new TopicsResponse();
        response.topics = result.topics.stream()
                .map(o -> topicResponseMapper.topicToTopicResponse(o))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private GetTopicByIdUseCase getTopicByIdUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<TopicResponse> getTopicById(
            @PathVariable("id") String id,
            @RequestParam(value = "showDeleted", required = false, defaultValue = "false") boolean showDeleted
    ) throws Exception {
        var parameters = new GetTopicByIdParameters();
        parameters.id = id;
        parameters.showDeleted = showDeleted;

        var result = getTopicByIdUseCase.execute(parameters);
        if (result.topic.isEmpty()) return ResponseEntity
                .notFound()
                .build();

        var response = topicResponseMapper
                .topicToTopicResponse(result.topic.get());
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private AddTopicUseCase addTopicUseCase;

    @Autowired
    private AddTopicParametersMapper addTopicParameterMapper;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<TopicResponse> addTopic(
            @RequestBody AddTopicRequest request
    ) throws Exception {
        var parameters = addTopicParameterMapper
                .addTopicRequestToAddTopicParameters(request);

        var result = addTopicUseCase.execute(parameters);
        var response = topicResponseMapper
                .topicToTopicResponse(result.topic);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private UpdateTopicUseCase updateTopicUseCase;

    @Autowired
    private UpdateTopicParametersMapper updateTopicParametersMapper;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<TopicResponse> updateAuthor(
            @PathVariable("id") String id,
            @RequestBody UpdateTopicRequest request
    ) throws Exception {
        var parameters = updateTopicParametersMapper
                .updateTopicRequestToUpdateTopicParameters(request, id);

        var result = updateTopicUseCase.execute(parameters);
        var response = topicResponseMapper
                .topicToTopicResponse(result.topic);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private ToggleTopicUseCase toggleTopicUseCase;

    @RequestMapping(value = "/{id}/toggle",
            method = RequestMethod.PUT,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<TopicResponse> toggleAuthor(
            @PathVariable("id") String id
    ) throws Exception {
        var parameters = new ToggleTopicParameters();
        parameters.id = id;

        var result = toggleTopicUseCase.execute(parameters);
        var response = topicResponseMapper
                .topicToTopicResponse(result.topic);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private DeleteTopicUseCase deleteTopicUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<Void> deleteTopic(
            @PathVariable("id") String id
    ) throws Exception {
        var parameters = new DeleteTopicParameters();
        parameters.id = id;

        deleteTopicUseCase.execute(parameters);
        return ResponseEntity
                .noContent()
                .build();
    }
}