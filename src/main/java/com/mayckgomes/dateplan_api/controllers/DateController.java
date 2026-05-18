package com.mayckgomes.dateplan_api.controllers;

import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.dto.date.CreateDateRequest;
import com.mayckgomes.dateplan_api.dto.date.DateResponse;
import com.mayckgomes.dateplan_api.dto.date.DeleteDateRequest;
import com.mayckgomes.dateplan_api.dto.date.EditDateRequest;
import com.mayckgomes.dateplan_api.services.DateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dates")
public class DateController {

    DateService dateService;

    public DateController(DateService dateService){
        this.dateService = dateService;
    }

    @GetMapping
    public ResponseEntity<List<DateResponse>> getDates(
            Authentication authentication
    ){

        UserDomain user = (UserDomain) authentication.getPrincipal();

        return ResponseEntity.ok(dateService.getDates(user.getRelationshipId()));

    }

    @PostMapping("/create")
    public ResponseEntity<DateResponse> createDate(
            @Valid @RequestBody CreateDateRequest dateRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(dateService.createDate(dateRequest));

    }

    @PutMapping("/edit")
    public ResponseEntity<DateResponse> editDate(
            @Valid @RequestBody EditDateRequest dateRequest){

        return ResponseEntity.ok(dateService.editDate(dateRequest));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteDate(@Valid @RequestBody DeleteDateRequest dateRequest){

        dateService.deleteDate(dateRequest.getDateId());

        return ResponseEntity.noContent().build();

    }

}
