package com.mayckgomes.dateplan_api.domains;

import com.mayckgomes.dateplan_api.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserDomain implements UserDetails {

    private Long id;
    private String publicId;
    private String name;
    private String email;
    private String password;
    private Long relationshipId;
    private String plan;
    private String notificationToken;

    public UserDomain(Long id, String publicId, String name, String email, Long relationshipId, String plan, String notificationToken) {
        this.id = id;
        this.publicId = publicId;
        this.name = name;
        this.email = email;
        this.relationshipId = relationshipId;
        this.plan = plan;
        this.notificationToken = notificationToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return name;
    }

    public UserResponse toUserResponse(){
        return new UserResponse(
                id,
                publicId,
                name,
                relationshipId,
                plan
        );
    }

}
