package com.exalt.infrastructure.advice;

import com.exalt.domain.exceptions.BankException;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;

import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(BankException.class)
    protected ProblemDetail handleNotFound(BankException ex) {
        var problemDetail =  ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Bank Account !");
        return problemDetail;
    }

    @GraphQlExceptionHandler(BankException.class)
    GraphQLError handle(BankException e,DataFetchingEnvironment env){
        e.printStackTrace();
        return GraphQLError.newError()
              .errorType(ErrorType.OperationNotSupported)
              .message(e.getMessage())
              .path(env.getExecutionStepInfo().getPath())
              .location(env.getField().getSourceLocation())
              .build();
    }

    @GraphQlExceptionHandler(Exception.class)
    GraphQLError handle(Exception e,DataFetchingEnvironment env){
        e.printStackTrace();
        return GraphQLError.newError()
              .errorType(ErrorType.OperationNotSupported)
              .message("Invalid your request")
              .path(env.getExecutionStepInfo().getPath())
              .location(env.getField().getSourceLocation())
              .build();
    }
}
