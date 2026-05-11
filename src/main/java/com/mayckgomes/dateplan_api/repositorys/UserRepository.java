package com.mayckgomes.dateplan_api.repositorys;

import com.mayckgomes.dateplan_api.entitys.UserEntity;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email) throws UserNotFoundException;

    Boolean existsByEmail(String email);

}
