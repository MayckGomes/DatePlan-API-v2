package com.mayckgomes.dateplan_api.controllers;


import com.mayckgomes.dateplan_api.dto.TokensResponse;
import com.mayckgomes.dateplan_api.dto.auth.LoginRequest;
import com.mayckgomes.dateplan_api.dto.auth.LogoutRequest;
import com.mayckgomes.dateplan_api.dto.auth.RegisterRequest;
import com.mayckgomes.dateplan_api.services.AuthServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServices authServices;

    public AuthController(AuthServices authServices) {

        this.authServices = authServices;
    }

    @PostMapping("/login")
    public ResponseEntity<TokensResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authServices.login(loginRequest));

    }

    @PostMapping("/register")
    public ResponseEntity<TokensResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(authServices.register(registerRequest));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<TokensResponse> refreshToken(@RequestHeader("Authorization") String token ){
        return ResponseEntity.ok(authServices.refreshToken(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest logoutRequest){
        authServices.logout(logoutRequest);

        return ResponseEntity.ok().build();
    }

}
