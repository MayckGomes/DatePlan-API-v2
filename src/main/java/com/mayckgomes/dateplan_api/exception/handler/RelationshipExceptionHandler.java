package com.mayckgomes.dateplan_api.exception.handler;

import com.mayckgomes.dateplan_api.exception.CustomErrorResponse;
import com.mayckgomes.dateplan_api.exception.custom.relationship.RelationshipNotFoundException;
import com.mayckgomes.dateplan_api.exception.custom.relationship.UserDontHaveRelationshipException;
import com.mayckgomes.dateplan_api.exception.custom.relationship.UserRelationshipNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RelationshipExceptionHandler {

    @ExceptionHandler(RelationshipNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handlerRelationshipNotFoundException(RelationshipNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(404,exception.getMessage()));
    }

    @ExceptionHandler(UserRelationshipNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handlerUserRelationshipNotFoundException(UserRelationshipNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(404,exception.getMessage()));
    }

    @ExceptionHandler(UserDontHaveRelationshipException.class)
    public ResponseEntity<CustomErrorResponse> handlerUserDontHaveRelationshipException(UserDontHaveRelationshipException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(404,exception.getMessage()));
    }

}
