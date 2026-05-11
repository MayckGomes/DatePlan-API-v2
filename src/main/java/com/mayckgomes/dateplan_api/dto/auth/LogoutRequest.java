package com.mayckgomes.dateplan_api.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LogoutRequest {

    @NotNull
    @NotEmpty
    private String accessToken;

    @NotNull
    @NotEmpty
    private String refreshToken;

}
