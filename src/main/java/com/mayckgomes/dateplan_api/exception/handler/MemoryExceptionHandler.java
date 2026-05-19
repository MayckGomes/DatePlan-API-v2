package com.mayckgomes.dateplan_api.exception.handler;

import com.mayckgomes.dateplan_api.exception.CustomErrorResponse;
import com.mayckgomes.dateplan_api.exception.custom.memory.MemoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemoryExceptionHandler {

    @ExceptionHandler(MemoryNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleMemoryNotFoundException(MemoryNotFoundException exception){

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(409, exception.getMessage()));
    }

}
