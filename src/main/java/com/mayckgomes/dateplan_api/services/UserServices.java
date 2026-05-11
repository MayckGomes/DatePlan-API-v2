package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.dto.user.UserResponse;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import com.mayckgomes.dateplan_api.repositorys.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements UserDetailsService {

    UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}
