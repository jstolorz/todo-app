package com.bluesoft.todoapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = IllegalExceptionProcessing.class)
class IllegalExceptioncontrollerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalStateException(IllegalStateException e){
        return ResponseEntity.badRequest().build();
    }

}
