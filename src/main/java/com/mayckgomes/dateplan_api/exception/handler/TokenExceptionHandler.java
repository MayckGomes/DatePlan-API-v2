package com.mayckgomes.dateplan_api.exception.handler;

import com.mayckgomes.dateplan_api.exception.CustomErrorResponse;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenExpiredException;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenInBlackListException;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<CustomErrorResponse> TokenExpiredExceptionHandler(TokenExpiredException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomErrorResponse(401,exception.getMessage()));
    }

    @ExceptionHandler(TokenInBlackListException.class)
    public ResponseEntity<CustomErrorResponse> TokenInBlackListExceptionHandler(TokenInBlackListException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomErrorResponse(401,exception.getMessage()));
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<CustomErrorResponse> TokenInvalidExceptionHandler(TokenInvalidException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorResponse(400,exception.getMessage()));
    }

}
