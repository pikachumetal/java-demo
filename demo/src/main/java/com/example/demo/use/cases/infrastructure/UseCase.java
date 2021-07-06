package com.example.demo.use.cases.infrastructure;

public interface UseCase<TIn, TOut> {
    TOut execute(TIn parameters) throws Exception;
}
