package com.example.demo.controllers;

import com.example.demo.contracts.authors.add.author.AddAuthorRequest;
import com.example.demo.contracts.authors.common.AuthorResponse;
import com.example.demo.contracts.authors.common.AuthorsResponse;
import com.example.demo.contracts.authors.update.author.UpdateAuthorRequest;
import com.example.demo.problems.GenericProblem;
import com.example.demo.use.cases.authors.add.AddAuthorParameters;
import com.example.demo.use.cases.authors.add.AddAuthorUseCase;
import com.example.demo.use.cases.authors.delete.DeleteAuthorParameters;
import com.example.demo.use.cases.authors.delete.DeleteAuthorUseCase;
import com.example.demo.use.cases.authors.get.by.id.GetAuthorByIdParameters;
import com.example.demo.use.cases.authors.get.by.id.GetAuthorByIdUseCase;
import com.example.demo.use.cases.authors.list.GetAuthorsParameters;
import com.example.demo.use.cases.authors.list.GetAuthorsUseCase;
import com.example.demo.use.cases.authors.update.UpdateAuthorParameters;
import com.example.demo.use.cases.authors.update.UpdateAuthorUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/authors")
@SuppressWarnings("unused")
public class AuthorsController {

    @SuppressWarnings("unused")
    @Autowired
    private ModelMapper modelMapper;

    @SuppressWarnings("unused")
    @Autowired
    private GetAuthorsUseCase getAuthorsUseCase;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<AuthorsResponse> getAuthors() throws Exception {
        var parameters = new GetAuthorsParameters();

        var result = getAuthorsUseCase.execute(parameters);

        var response = new AuthorsResponse();
        response.authors = result.authors.stream()
                .map(o -> modelMapper.map(o, AuthorResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private GetAuthorByIdUseCase getAuthorByIdUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<AuthorResponse> getAuthorById(
            @PathVariable("id") String id,
            @RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted
    ) throws Exception {
        var parameters = new GetAuthorByIdParameters();
        parameters.id = id;
        parameters.isDeleted = isDeleted;

        var result = getAuthorByIdUseCase.execute(parameters);
        if (result.author.isEmpty()) return ResponseEntity
                .notFound()
                .build();

        var response = modelMapper.map(result.author.get(), AuthorResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private AddAuthorUseCase addAuthorUseCase;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<AuthorResponse> addAuthor(@RequestBody AddAuthorRequest request) throws Exception {
        var parameters = modelMapper.map(request, AddAuthorParameters.class);
        var result = addAuthorUseCase.execute(parameters);
        var response = modelMapper.map(result.author, AuthorResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private UpdateAuthorUseCase updateAuthorUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable("id") String id, @RequestBody UpdateAuthorRequest request) throws Exception {
        if (!id.equals(request.id)) throw new GenericProblem("Path id and Request Id are not equals!");

        var parameters = modelMapper.map(request, UpdateAuthorParameters.class);
        var result = updateAuthorUseCase.execute(parameters);
        var response = modelMapper.map(result.author, AuthorResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private DeleteAuthorUseCase deleteAuthorUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") String id) throws Exception {
        var parameters = new DeleteAuthorParameters();
        parameters.id = id;

        deleteAuthorUseCase.execute(parameters);
        return ResponseEntity
                .noContent()
                .build();
    }
}