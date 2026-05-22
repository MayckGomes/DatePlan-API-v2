package com.mayckgomes.dateplan_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ServerController {

    @GetMapping("")
    public ResponseEntity<Void> checkStatus(){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
