package com.mayckgomes.dateplan_api.services;

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
}
