package com.questionpro.grocery.exceptions;

import com.questionpro.grocery.response.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessages(exception.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException
            (HttpMessageNotReadableException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessages(List.of(exception.getMessage())).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException
            (EntityNotFoundException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .responseCode(HttpStatus.NOT_FOUND.value())
                .responseMessages(List.of(exception.getMessage())).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
