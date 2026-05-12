package com.mayckgomes.dateplan_api.controllers;

import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.dto.user.ChangeNameRequest;
import com.mayckgomes.dateplan_api.dto.user.ChangePasswordRequest;
import com.mayckgomes.dateplan_api.dto.user.DeleteUserRequest;
import com.mayckgomes.dateplan_api.dto.user.UserRelationshipIdResponse;
import com.mayckgomes.dateplan_api.services.UserService;
import org.aspectj.asm.internal.Relationship;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/changeName")
    public ResponseEntity<Void> changeName(Authentication authentication, @RequestBody ChangeNameRequest changeNameRequest) {
        UserDomain user = (UserDomain) authentication.getPrincipal();

        userService.changeUserName(user.getId(),changeNameRequest.getNewName());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<Void> changePassword(
            Authentication authentication,
            @RequestHeader("Authentication") String accessToken,
            @RequestBody ChangePasswordRequest changePasswordRequest) {

        UserDomain user = (UserDomain) authentication.getPrincipal();

        userService.changeUserPassword(user.getId(),changePasswordRequest.getNewPassword(),accessToken,changePasswordRequest.getRefreshToken());

        return ResponseEntity.ok().build();

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            Authentication authentication,
            @RequestHeader("Authentication") String accessToken,
            @RequestBody DeleteUserRequest deleteUserRequest) {

        UserDomain user = (UserDomain) authentication.getPrincipal();

        userService.deleteUser(user.getId(), accessToken, deleteUserRequest);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/relationship")
    public ResponseEntity<UserRelationshipIdResponse> getRelationshipId(Authentication authentication) {
        UserDomain user = (UserDomain) authentication.getPrincipal();

        return ResponseEntity.ok(userService.getUserRelationshipId(user.getId()));

    }
}
