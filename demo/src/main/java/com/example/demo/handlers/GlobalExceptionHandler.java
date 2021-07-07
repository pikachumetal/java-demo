package com.example.demo.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.net.URI;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            NativeWebRequest request
    ) {
        var errors = ex.getBindingResult()
                .fieldErrors
                .stream()
                .map(o -> Problem.builder()
                        .with("field", Objects.requireNonNull(o.field))
                        .with("message", Objects.requireNonNull(o.defaultMessage))
                        .build())
                .collect(Collectors.toList());

        ProblemBuilder builder = Problem
                .builder()
                .withType(URI.create("http://example.com/problems/multiple/validations"))
                .withStatus(Status.BAD_REQUEST)
                .withTitle("Validation Problems")
                .withDetail(errors.size() <= 1 ? "There is a validation error" : "There ara multiple validation errors")
                .with("validations", errors);

        return new ResponseEntity<Problem>(builder.build(), HttpStatus.BAD_REQUEST);
    }
}
