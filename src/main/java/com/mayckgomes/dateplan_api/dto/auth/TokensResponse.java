package com.mayckgomes.dateplan_api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TokensResponse {

    private String accessToken;
    private String refreshToken;

}
