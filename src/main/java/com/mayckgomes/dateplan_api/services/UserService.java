package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.dto.user.UpdateNotificationTokenRequest;
import com.mayckgomes.dateplan_api.exception.custom.invite.UserHaveARelationshipException;
import com.mayckgomes.dateplan_api.jwt.JwtService;
import com.mayckgomes.dateplan_api.dto.user.DeleteUserRequest;
import com.mayckgomes.dateplan_api.dto.user.UserRelationshipIdResponse;
import com.mayckgomes.dateplan_api.exception.custom.user.UserIdInvalidException;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import com.mayckgomes.dateplan_api.repositorys.UsersRepository;
import com.mayckgomes.dateplan_api.utils.VerifyTokenText;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    UsersRepository usersRepository;
    PasswordEncoder passwordEncoder;
    RedisBlackListService redisBlackListService;
    JwtService jwtService;

    public UserService(UsersRepository usersRepository,
                       PasswordEncoder passwordEncoder,
                       RedisBlackListService redisBlackListService,
                       JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisBlackListService = redisBlackListService;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        var user = usersRepository.findByEmail(username);

        if (user == null){

            throw new UserNotFoundException();

        }

        return user.toUserDomain();
    }

    public void changeUserName(Long userId,String newName){

        var targetUser = usersRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        targetUser.setName(newName);

        usersRepository.save(targetUser);

    }

    public void changeUserPassword(Long userId,String newPassword, String accessToken, String refreshToken){

        accessToken = VerifyTokenText.verifyTokenText(accessToken);

        var targetUser = usersRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        var encryptedPassword = (passwordEncoder.encode(newPassword));

        targetUser.setPassword(encryptedPassword);

        usersRepository.save(targetUser);

        redisBlackListService.addAccessToken(jwtService.getTokenId(accessToken),accessToken);
        redisBlackListService.addRefreshToken(jwtService.getTokenId(refreshToken),refreshToken);

    }

    public void deleteUser(Long accessTokenUserId, String accessToken, DeleteUserRequest deleteUserRequest) {

        accessToken = VerifyTokenText.verifyTokenText(accessToken);

        if (!accessTokenUserId.equals(deleteUserRequest.getId())) {

            throw new UserIdInvalidException();
        }

        var targetUser = usersRepository.findById(accessTokenUserId).orElseThrow(UserNotFoundException::new);

        if (targetUser.getRelationshipId() != null) {

            throw new UserHaveARelationshipException();

        }

        usersRepository.delete(targetUser);

        redisBlackListService.addAccessToken(jwtService.getTokenId(accessToken),accessToken);
        redisBlackListService.addRefreshToken(jwtService.getTokenId(deleteUserRequest.getRefreshToken()), deleteUserRequest.getRefreshToken());

    }

    public UserRelationshipIdResponse getRelationshipId(Long userId){
        var targetUser = usersRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        var relationshipid = targetUser.getRelationshipId().toString();

        if (relationshipid.equals(null)){
            relationshipid = "null";
        } else {
            relationshipid.toString();
        }

        return new UserRelationshipIdResponse(relationshipid);
    }

    @Transactional
    public void updateNotificationToken(Long userId, UpdateNotificationTokenRequest notificationTokenRequest){

        var targetUser = usersRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        targetUser.setNotificationToken(notificationTokenRequest.getNotificationToken());

        usersRepository.save(targetUser);

    }

}
