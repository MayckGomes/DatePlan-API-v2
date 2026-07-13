package com.mayckgomes.dateplan_api.repositorys;

import com.mayckgomes.dateplan_api.entitys.UsersEntity;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByEmail(String email) throws UserNotFoundException;

    Boolean existsByEmail(String email);

    Optional<UsersEntity> findByPublicId(String publicId);

    UsersEntity findByExternalProviderId(String externalProviderId);

    @Modifying
    @Transactional
    @NativeQuery("UPDATE users SET accept_policy_privacy_version = :versionNum WHERE id = :id")
    int updatePolicyPrivacyVersionById(@Param("versionNum") Long version, @Param("id") Long id);

    @Modifying
    @Transactional
    @NativeQuery("UPDATE users SET policy_privacy_accepted_at = :date WHERE id = :id")
    int updatePolicyPrivacyAcceptedAtById(@Param("date") String date, @Param("id") Long id);

    @Modifying
    @Transactional
    @NativeQuery("UPDATE users SET accept_terms_of_use_version = :versionNum WHERE id = :id")
    int updateTermsOfUseVersionById(@Param("versionNum") Long version, @Param("id") Long id);

    @Modifying
    @Transactional
    @NativeQuery("UPDATE users SET terms_of_use_accepted_at = :date WHERE id = :id")
    int updateTermsOfUseAcceptedAtById(@Param("date") String date, @Param("id") Long id);

    @Modifying
    @Transactional
    @NativeQuery("UPDATE users SET notification_token = '' WHERE id = :id")
    int clearNotificationToken(@Param("id") Long id);

}
