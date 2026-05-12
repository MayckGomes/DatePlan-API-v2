package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.auth.JwtService;
import com.mayckgomes.dateplan_api.dto.user.DeleteUserRequest;
import com.mayckgomes.dateplan_api.dto.user.UserRelationshipIdResponse;
import com.mayckgomes.dateplan_api.entitys.UserEntity;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenInvalidException;
import com.mayckgomes.dateplan_api.exception.custom.user.UserIdInvalidException;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import com.mayckgomes.dateplan_api.repositorys.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RedisBlackListService redisBlackListService;
    JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RedisBlackListService redisBlackListService,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisBlackListService = redisBlackListService;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        var user = userRepository.findByEmail(username);

        if (user == null){

            throw new UserNotFoundException();

        }

        return user.toUserDomain();
    }

    public void changeUserName(Long userId,String newName){

        var targetUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        targetUser.setName(newName);

        userRepository.save(targetUser);

    }

    public void changeUserPassword(Long userId,String newPassword, String accessToken, String refreshToken){

        if (accessToken.isEmpty() || accessToken == null || !accessToken.startsWith("Bearer ")) {

            throw new TokenInvalidException();

        }

        accessToken = accessToken.replace("Bearer ", "");

        var targetUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        var encryptedPassword = (passwordEncoder.encode(newPassword));

        targetUser.setPassword(encryptedPassword);

        userRepository.save(targetUser);

        redisBlackListService.addAccessToken(jwtService.getTokenId(accessToken),accessToken);
        redisBlackListService.addRefreshToken(jwtService.getTokenId(refreshToken),refreshToken);

    }

    public void deleteUser(Long accessTokenUserId, String accessToken, DeleteUserRequest deleteUserRequest) {

        if (!accessTokenUserId.equals(deleteUserRequest.getId())) {

            throw new UserIdInvalidException();
        }

        var targetUser = userRepository.findById(accessTokenUserId).orElseThrow(UserNotFoundException::new);

        userRepository.delete(targetUser);

        redisBlackListService.addAccessToken(jwtService.getTokenId(accessToken),accessToken);
        redisBlackListService.addRefreshToken(jwtService.getTokenId(deleteUserRequest.getRefreshToken()), deleteUserRequest.getRefreshToken());

    }

    public UserRelationshipIdResponse getRelationshipId(Long userId){
        var targetUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return new UserRelationshipIdResponse(targetUser.getRelationshipId());
    }

}
