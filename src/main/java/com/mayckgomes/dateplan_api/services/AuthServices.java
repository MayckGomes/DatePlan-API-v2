package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.auth.JwtService;
import com.mayckgomes.dateplan_api.dto.auth.TokensResponse;
import com.mayckgomes.dateplan_api.dto.auth.LoginRequest;
import com.mayckgomes.dateplan_api.dto.auth.LogoutRequest;
import com.mayckgomes.dateplan_api.dto.auth.RegisterRequest;
import com.mayckgomes.dateplan_api.entitys.UserEntity;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenInvalidException;
import com.mayckgomes.dateplan_api.exception.custom.user.UserAlreadyExistsException;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import com.mayckgomes.dateplan_api.repositorys.UserRepository;
import com.mayckgomes.dateplan_api.utils.CreatePublicId;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    PasswordEncoder passwordEncoder;
    RedisBlackListService redisBlackListService;


    public AuthServices(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            RedisBlackListService redisBlackListService
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.redisBlackListService = redisBlackListService;
    }

    public TokensResponse login(LoginRequest loginRequest){
        var userLogin = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        try {
            authenticationManager.authenticate(userLogin);

        } catch (BadCredentialsException e) {
            throw new UserNotFoundException();
        }

        var user = userRepository.findByEmail(loginRequest.getEmail());

        return jwtService.createTokens(user.toUserDomain());

    }

    public TokensResponse register(RegisterRequest registerRequest){

        var exists = userRepository.existsByEmail(registerRequest.getEmail());

        if(exists){
            throw new UserAlreadyExistsException();
        }

        var newUser = new UserEntity();

        var defaultPlan = "Free";
        var defaultNotificationToken = "";

        var publicId = CreatePublicId.create(registerRequest.getEmail(), registerRequest.getName());

        var encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());

        newUser.setEmail(registerRequest.getEmail());
        newUser.setName(registerRequest.getName());
        newUser.setPassword(encryptedPassword);
        newUser.setPublicId(publicId);
        newUser.setRelationshipId(null);
        newUser.setNotificationToken(defaultNotificationToken);
        newUser.setPlan(defaultPlan);

        var savedUser = userRepository.save(newUser);

        return jwtService.createTokens(savedUser.toUserDomain());

    }

    public TokensResponse refreshToken(String token){

        if (!token.startsWith("Bearer ") || token.isEmpty() || token == null){
            throw new TokenInvalidException();
        }

        var tokenDecoded = jwtService.decodeRefreshToken(token);

        var userId = tokenDecoded.getUserId();

        redisBlackListService.addRefreshToken(tokenDecoded.getJwtid(),token);

        UserEntity user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return jwtService.createTokens(user.toUserDomain());

    }

    public void logout(LogoutRequest logoutRequest){

        redisBlackListService.addAccessToken(jwtService.getTokenId(logoutRequest.getAccessToken()),logoutRequest.getAccessToken());
        redisBlackListService.addRefreshToken(jwtService.getTokenId(logoutRequest.getRefreshToken()),logoutRequest.getAccessToken());

    }

}
