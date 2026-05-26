package com.mayckgomes.dateplan_api.controllers;

import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.dto.relationship.ChangeInitialDayRequest;
import com.mayckgomes.dateplan_api.dto.relationship.DeleteRelationshipResponse;
import com.mayckgomes.dateplan_api.dto.relationship.RelationshipResponse;
import com.mayckgomes.dateplan_api.services.RelationshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relationship")
public class RelationshipController {

    RelationshipService relationshipService;

    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @GetMapping("/")
    public ResponseEntity<RelationshipResponse> getRelationshipById(Authentication authentication){

        var user = (UserDomain) authentication.getPrincipal();

        return ResponseEntity.ok(relationshipService.getRelationshipById(user.getRelationshipId()));
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteRelationshipById(
            Authentication authentication,
            @RequestHeader("Authentication") String refreshToken
    ){

        var user = (UserDomain) authentication.getPrincipal();

        relationshipService.deleteRelationshipById(user);

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/initialDay")
    public ResponseEntity<Void> changeInitialDay(Authentication authentication, @RequestBody ChangeInitialDayRequest initialDayRequest){

        var user = (UserDomain) authentication.getPrincipal();

        relationshipService.changeInitialDay(user.getRelationshipId(), initialDayRequest.getInitialDay());

        return ResponseEntity.ok().build();

    }

}
