package com.mayckgomes.dateplan_api.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.mayckgomes.dateplan_api.dto.auth.*;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenInvalidException;
import com.mayckgomes.dateplan_api.jwt.JwtService;
import com.mayckgomes.dateplan_api.entitys.UsersEntity;
import com.mayckgomes.dateplan_api.exception.custom.user.UserAlreadyExistsException;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import com.mayckgomes.dateplan_api.repositorys.UsersRepository;
import com.mayckgomes.dateplan_api.utils.CreatePublicId;
import com.mayckgomes.dateplan_api.utils.VerifyTokenText;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UsersRepository usersRepository;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    PasswordEncoder passwordEncoder;
    RedisBlackListService redisBlackListService;


    public AuthService(
            UsersRepository usersRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            RedisBlackListService redisBlackListService
    ) {
        this.usersRepository = usersRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.redisBlackListService = redisBlackListService;
    }

    public AuthResponse login(LoginRequest loginRequest){
        var userLogin = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        try {
            authenticationManager.authenticate(userLogin);

        } catch (BadCredentialsException e) {
            throw new UserNotFoundException();
        }

        var targetUser = usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);

        var tokens = jwtService.createTokens(targetUser.toUserDomain());

        var user = targetUser.toUserDomain().toUserResponse();

        return new AuthResponse(tokens,user);

    }

    public AuthResponse autoLogin(TokensRequest tokensRequest){

        var refresh = jwtService.decodeRefreshToken(tokensRequest.getRefreshToken());

        var targetUser = usersRepository.findById(refresh.getUserId())
                .orElseThrow(UserNotFoundException::new);

        redisBlackListService.addAccessToken(jwtService.getTokenId(tokensRequest.getAccessToken()), tokensRequest.getAccessToken());
        redisBlackListService.addRefreshToken(jwtService.getTokenId(tokensRequest.getRefreshToken()), tokensRequest.getRefreshToken());

        var tokens = jwtService.createTokens(targetUser.toUserDomain());

        var user = targetUser.toUserDomain().toUserResponse();

        return new AuthResponse(tokens,user);

    }

    public AuthResponse register(RegisterRequest registerRequest){

        var exists = usersRepository.existsByEmail(registerRequest.getEmail());

        if(exists){
            throw new UserAlreadyExistsException();
        }

        var defaultPlan = "FREE";
        var defaultAccountType = "APP";
        var defaultNotificationToken = "";

        var publicId = CreatePublicId.create(registerRequest.getEmail(), registerRequest.getName());

        var encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());

        var newUser = UsersEntity.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .password(encryptedPassword)
                .publicId(publicId)
                .relationshipId(null)
                .notificationToken(defaultNotificationToken)
                .plan(defaultPlan)
                .accountType(defaultAccountType)
                .acceptPolicyPrivacyVersion(registerRequest.getPolicyPrivacyAcceptedVersion())
                .policyPrivacyAcceptedAt(registerRequest.getPolicyPrivacyAcceptedAt())
                .acceptTermsOfUseVersion(registerRequest.getTermsOfUseAcceptedVersion())
                .termsOfUseAcceptedAt(registerRequest.getTermsOfUseAcceptedAt())
                .build();

        var savedUser = usersRepository.save(newUser);

        var tokens = jwtService.createTokens(savedUser.toUserDomain());

        var user = savedUser.toUserDomain().toUserResponse();

        return new AuthResponse(tokens,user);

    }

    public AuthResponse googleAuth(GoogleRequest googleRequest) {

        FirebaseToken googleUser;

        try {
            googleUser = FirebaseAuth.getInstance().verifyIdToken(googleRequest.getToken());
        } catch (FirebaseAuthException exception) {
            System.out.println(exception.getMessage());
            throw new TokenInvalidException();
        }

        var user = usersRepository.findByExternalProviderId(googleUser.getUid());

        if (user == null){

            var exists = usersRepository.existsByEmail(googleUser.getEmail());

            if (exists){
                throw new UserAlreadyExistsException();
            }

            var defaultPlan = "FREE";
            var defaultNotificationToken = "";
            var publicId = CreatePublicId.create(googleUser.getEmail(), googleUser.getName());

            var newUser = UsersEntity.builder()
                    .email(googleUser.getEmail())
                    .name(googleUser.getName())
                    .password(null)
                    .externalProviderId(googleUser.getUid())
                    .publicId(publicId)
                    .relationshipId(null)
                    .notificationToken(defaultNotificationToken)
                    .plan(defaultPlan)
                    .accountType("GOOGLE")
                    .acceptPolicyPrivacyVersion(0L)
                    .policyPrivacyAcceptedAt("not found")
                    .acceptTermsOfUseVersion(0L)
                    .termsOfUseAcceptedAt("not found")
                    .build();


            user = usersRepository.save(newUser);

        } else {

            user.setEmail(googleUser.getEmail());

            usersRepository.save(user);

        }

        var tokens = jwtService.createTokens(user.toUserDomain());

        return new AuthResponse(tokens,user.toUserDomain().toUserResponse());

    }

    public TokensResponse refreshToken(String token){

        token = VerifyTokenText.verifyTokenText(token);

        var tokenDecoded = jwtService.decodeRefreshToken(token);

        var userId = tokenDecoded.getUserId();

        redisBlackListService.addRefreshToken(tokenDecoded.getJwtid(),token);

        UsersEntity user = usersRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return jwtService.createTokens(user.toUserDomain());

    }

    @Transactional
    public void logout(LogoutRequest logoutRequest, Long userId){

        usersRepository.clearNotificationToken(userId);

        redisBlackListService.addAccessToken(jwtService.getTokenId(logoutRequest.getAccessToken()),logoutRequest.getAccessToken());
        redisBlackListService.addRefreshToken(jwtService.getTokenId(logoutRequest.getRefreshToken()),logoutRequest.getAccessToken());

    }

}
