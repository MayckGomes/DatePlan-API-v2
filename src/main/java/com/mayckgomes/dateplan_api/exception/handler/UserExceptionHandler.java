package com.mayckgomes.dateplan_api.exception.handler;

import com.mayckgomes.dateplan_api.exception.CustomErrorResponse;
import com.mayckgomes.dateplan_api.exception.custom.user.UserAlreadyExistsException;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(404,exception.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(409,exception.getMessage()));
    }

}
