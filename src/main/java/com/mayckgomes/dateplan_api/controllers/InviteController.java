package com.mayckgomes.dateplan_api.controllers;

import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.dto.invite.*;
import com.mayckgomes.dateplan_api.services.InviteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invites")
public class InviteController {

    InviteService inviteService;

    public InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateInviteResponse> createInvite(@Valid @RequestBody CreateInviteRequest createInviteRequest) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(inviteService.createInvite(createInviteRequest));
    }

    @GetMapping("/toMe")
    public ResponseEntity<List<InviteResponse>> getInvitesByToUser(Authentication authentication) {

        var user = (UserDomain) authentication.getPrincipal();

        return ResponseEntity.ok(inviteService.getAllInvitesByToId(user.getId()));

    }

    @PostMapping("/accept")
    public ResponseEntity<AcceptInviteResponse> acceptInvite(
            Authentication authentication,
            @RequestBody AcceptInviteRequest invite) {

        UserDomain user = (UserDomain) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(inviteService.acceptInvite(user.getId() ,invite));
    }

    @PostMapping("/decline")
    public ResponseEntity<Void> declineInvite(
            Authentication authentication,
            @RequestBody DeclineInviteRequest invite) {

        UserDomain user = (UserDomain) authentication.getPrincipal();

        inviteService.declineInvite(user.getId() ,invite);

        return ResponseEntity.ok().build();
    }

}
