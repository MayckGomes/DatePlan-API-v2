package com.mayckgomes.dateplan_api.controllers;

import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.dto.user.ChangeNameRequest;
import com.mayckgomes.dateplan_api.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PatchMapping("/changeName")
    public ResponseEntity<Void> changeName(Authentication authentication, @RequestBody ChangeNameRequest changeNameRequest) {
        UserDomain user = (UserDomain) authentication.getPrincipal();

        userServices.changeUserName(user.getId(),changeNameRequest.getNewName());

        return ResponseEntity.ok().build();
    }

}
