package com.portfolio.todo_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class TodoAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;


}
