package com.mayckgomes.dateplan_api.repositorys;

import com.mayckgomes.dateplan_api.entitys.UsersEntity;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByEmail(String email) throws UserNotFoundException;

    Boolean existsByEmail(String email);

    Optional<UsersEntity> findByPublicId(String publicId);

    UsersEntity findByExternalProviderId(String externalProviderId);

}
