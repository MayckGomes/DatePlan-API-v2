package com.mayckgomes.dateplan_api.entitys;

import com.mayckgomes.dateplan_api.domains.UserDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String publicId;
    private String name;
    private String email;
    private String password;
    private Long relationshipId;
    private String plan;
    private String notificationToken;
    private Long acceptPolicyPrivacyVersion;
    private String acceptedAt;

    public UserDomain toUserDomain(){
        return new UserDomain(
                id,
                publicId,
                name,
                email,
                password,
                relationshipId,
                plan,
                notificationToken,
                acceptPolicyPrivacyVersion,
                acceptedAt
        );
    }

}
