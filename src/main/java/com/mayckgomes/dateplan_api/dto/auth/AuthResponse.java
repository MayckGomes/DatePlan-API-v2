package com.mayckgomes.dateplan_api.dto.auth;

import com.mayckgomes.dateplan_api.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {

    private TokensResponse tokens;
    private UserResponse user;

}
