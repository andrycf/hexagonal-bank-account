package com.exalt.infrastructure.advice;

import com.exalt.domain.exceptions.BankException;
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
}
