package com.example.demo.controllers;

import com.example.demo.contracts.categories.add.category.AddCategoryRequest;
import com.example.demo.contracts.categories.common.CategoriesResponse;
import com.example.demo.contracts.categories.common.CategoryResponse;
import com.example.demo.contracts.categories.update.category.UpdateCategoryRequest;
import com.example.demo.problems.GenericProblem;
import com.example.demo.use.cases.categories.add.AddCategoryParameters;
import com.example.demo.use.cases.categories.add.AddCategoryUseCase;
import com.example.demo.use.cases.categories.delete.DeleteCategoryParameters;
import com.example.demo.use.cases.categories.delete.DeleteCategoryUseCase;
import com.example.demo.use.cases.categories.get.by.id.GetCategoryByIdParameters;
import com.example.demo.use.cases.categories.get.by.id.GetCategoryByIdUseCase;
import com.example.demo.use.cases.categories.list.GetCategoriesParameters;
import com.example.demo.use.cases.categories.list.GetCategoriesUseCase;
import com.example.demo.use.cases.categories.toggle.ToggleCategoryParameters;
import com.example.demo.use.cases.categories.toggle.ToggleCategoryUseCase;
import com.example.demo.use.cases.categories.update.UpdateCategoryParameters;
import com.example.demo.use.cases.categories.update.UpdateCategoryUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/categories")
@SuppressWarnings("unused")
public class CategoriesController {

    @SuppressWarnings("unused")
    @Autowired
    private ModelMapper modelMapper;

    @SuppressWarnings("unused")
    @Autowired
    private GetCategoriesUseCase getCategoriesUseCase;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<CategoriesResponse> getCategories() throws Exception {
        var parameters = new GetCategoriesParameters();

        var result = getCategoriesUseCase.execute(parameters);

        var response = new CategoriesResponse();
        response.categories = result.categories.stream()
                .map(o -> modelMapper.map(o, CategoryResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable("id") String id,
            @RequestParam(value = "showDeleted", required = false, defaultValue = "false") boolean showDeleted
    ) throws Exception {
        var parameters = new GetCategoryByIdParameters();
        parameters.id = id;
        parameters.showDeleted = showDeleted;

        var result = getCategoryByIdUseCase.execute(parameters);
        if (result.category.isEmpty()) return ResponseEntity
                .notFound()
                .build();

        var response = modelMapper.map(result.category.get(), CategoryResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private AddCategoryUseCase addCategoryUseCase;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<CategoryResponse> addCategory(
            @RequestBody AddCategoryRequest request
    ) throws Exception {
        var parameters = modelMapper.map(request, AddCategoryParameters.class);
        var result = addCategoryUseCase.execute(parameters);
        var response = modelMapper.map(result.category, CategoryResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private UpdateCategoryUseCase updateCategoryUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<CategoryResponse> updateAuthor(
            @PathVariable("id") String id,
            @RequestBody UpdateCategoryRequest request
    ) throws Exception {
        var parameters = modelMapper.map(request, UpdateCategoryParameters.class);
        parameters.id = id;

        var result = updateCategoryUseCase.execute(parameters);
        var response = modelMapper.map(result.category, CategoryResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private ToggleCategoryUseCase toggleCategoryUseCase;

    @RequestMapping(value = "/{id}/toggle",
            method = RequestMethod.PUT,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<CategoryResponse> toggleAuthor(
            @PathVariable("id") String id
    ) throws Exception {
        var parameters = new ToggleCategoryParameters();
        parameters.id = id;

        var result = toggleCategoryUseCase.execute(parameters);
        var response = modelMapper.map(result.category, CategoryResponse.class);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Autowired
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<Void> deleteCategory(
            @PathVariable("id") String id
    ) throws Exception {
        var parameters = new DeleteCategoryParameters();
        parameters.id = id;

        deleteCategoryUseCase.execute(parameters);
        return ResponseEntity
                .noContent()
                .build();
    }
}