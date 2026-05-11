package com.mayckgomes.dateplan_api.entitys;

import com.mayckgomes.dateplan_api.domains.UserDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Users")
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

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

    public UserDomain toUserDomain(){
        return new UserDomain(
                id,
                publicId,
                name,
                email,
                password,
                relationshipId,
                plan,
                notificationToken
        );
    }

}
