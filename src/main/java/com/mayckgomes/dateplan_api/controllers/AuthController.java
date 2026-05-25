package com.mayckgomes.dateplan_api.controllers;


import com.mayckgomes.dateplan_api.dto.auth.*;
import com.mayckgomes.dateplan_api.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));

    }

    @PostMapping("/autoLogin")
    public ResponseEntity<AuthResponse> autoLogin(@Valid @RequestBody TokensRequest tokensRequest){
        return ResponseEntity.ok(authService.autoLogin(tokensRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<TokensResponse> refreshToken(@Valid @RequestHeader("Authorization") String token ){
        return ResponseEntity.ok(authService.refreshToken(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest logoutRequest){
        authService.logout(logoutRequest);

        return ResponseEntity.ok().build();
    }

}
