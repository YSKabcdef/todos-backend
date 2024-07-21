package com.portfolio.todo_management.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TodoAPIException.class)
    public ResponseEntity<ErrorDetails> handleTodoAPIException(TodoAPIException todoAPIException,WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(webRequest.getDescription(false));
        errorDetails.setMessage(todoAPIException.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(webRequest.getDescription(false));
        errorDetails.setMessage(resourceNotFoundException.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAuthorizationDeniedException(AuthorizationDeniedException exception,WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(webRequest.getDescription(false));
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGeneralException(Exception exception,WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(webRequest.getDescription(false));
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
