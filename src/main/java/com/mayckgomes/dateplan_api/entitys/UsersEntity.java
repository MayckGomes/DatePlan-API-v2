package com.mayckgomes.dateplan_api.entitys;

import com.mayckgomes.dateplan_api.domains.UserDomain;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String publicId;
    private String name;
    private String email;
    private String password;
    private String externalProviderId;

    private Long relationshipId;
    private String plan;
    private String accountType;
    private String notificationToken;

    private Long acceptPolicyPrivacyVersion;
    private String policyPrivacyAcceptedAt;
    private Long acceptTermsOfUseVersion;
    private String termsOfUseAcceptedAt;

    public UserDomain toUserDomain(){
        return new UserDomain(
                id,
                publicId,
                name,
                email,
                password,
                externalProviderId,
                relationshipId,
                plan,
                notificationToken,
                acceptPolicyPrivacyVersion,
                acceptTermsOfUseVersion
        );
    }

}
