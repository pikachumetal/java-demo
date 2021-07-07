package com.example.demo.controllers;

import com.example.demo.contracts.questions.common.QuestionResponse;
import com.example.demo.contracts.topics.add.topic.AddTopicRequest;
import com.example.demo.contracts.topics.common.TopicsResponse;
import com.example.demo.contracts.topics.common.TopicResponse;
import com.example.demo.contracts.topics.update.topic.UpdateTopicRequest;
import com.example.demo.use.cases.topics.add.AddTopicParameters;
import com.example.demo.use.cases.topics.add.AddTopicUseCase;
import com.example.demo.use.cases.topics.delete.DeleteTopicParameters;
import com.example.demo.use.cases.topics.delete.DeleteTopicUseCase;
import com.example.demo.use.cases.topics.get.by.id.GetTopicByIdParameters;
import com.example.demo.use.cases.topics.get.by.id.GetTopicByIdUseCase;
import com.example.demo.use.cases.topics.list.GetTopicsParameters;
import com.example.demo.use.cases.topics.list.GetTopicsUseCase;
import com.example.demo.use.cases.topics.toggle.ToggleTopicParameters;
import com.example.demo.use.cases.topics.toggle.ToggleTopicUseCase;
import com.example.demo.use.cases.topics.update.UpdateTopicParameters;
import com.example.demo.use.cases.topics.update.UpdateTopicUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/topics")
@SuppressWarnings("unused")
public class TopicsController {

    @SuppressWarnings("unused")
    @Autowired
    private ModelMapper modelMapper;

    @SuppressWarnings("unused")
    @Autowired
    private GetTopicsUseCase getTopicsUseCase;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<TopicsResponse> getTopics() throws Exception {
        var parameters = new GetTopicsParameters();

        var result = getTopicsUseCase.execute(parameters);

        var response = new TopicsResponse();
        response.topics = mapList(result.topics, TopicResponse.class);

        return ResponseEntity
                .ok()
                .body(response);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
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

        var response = modelMapper.map(result.topic.get(), TopicResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private AddTopicUseCase addTopicUseCase;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<TopicResponse> addTopic(
            @RequestBody AddTopicRequest request
    ) throws Exception {
        var parameters = modelMapper.map(request, AddTopicParameters.class);
        var result = addTopicUseCase.execute(parameters);
        var response = modelMapper.map(result.topic, TopicResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private UpdateTopicUseCase updateTopicUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<TopicResponse> updateAuthor(
            @PathVariable("id") String id,
            @RequestBody UpdateTopicRequest request
    ) throws Exception {
        var parameters = modelMapper.map(request, UpdateTopicParameters.class);
        parameters.id = id;

        var result = updateTopicUseCase.execute(parameters);
        var response = modelMapper.map(result.topic, TopicResponse.class);
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
        var response = modelMapper.map(result.topic, TopicResponse.class);
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