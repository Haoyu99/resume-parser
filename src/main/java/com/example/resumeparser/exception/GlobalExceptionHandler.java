package com.example.resumeparser.exception;

import com.example.resumeparser.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/***
 * @title GlobalExceptionHandler
 * @description
 * @author haoyu99
 * @version 1.0.0
 * @create 2024/7/21 10:16
 **/
@ControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<String>> handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(Response.error(500, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<String>> handleJsonProcessingException(JsonProcessingException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.error(500, "JSON processing error: " + ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
