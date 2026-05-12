package com.mayckgomes.dateplan_api.exception.handler;

import com.mayckgomes.dateplan_api.exception.CustomErrorResponse;
import com.mayckgomes.dateplan_api.exception.custom.invite.InviteNotExistsException;
import com.mayckgomes.dateplan_api.exception.custom.invite.ToUserAlreadyInRelationshipException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InviteExceptionHandler {

    @ExceptionHandler(InviteNotExistsException.class)
    public ResponseEntity<CustomErrorResponse> handleInviteNotExistsException(InviteNotExistsException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(404, exception.getMessage()));
    }

    @ExceptionHandler(ToUserAlreadyInRelationshipException.class)
    public ResponseEntity<CustomErrorResponse> handleToUserAlreadyInRelationship(ToUserAlreadyInRelationshipException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(409, exception.getMessage()));
    }

}
