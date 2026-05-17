package com.mayckgomes.dateplan_api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LogoutRequest {

    @NotNull
    @NotBlank
    private String accessToken;

    @NotNull
    @NotBlank
    private String refreshToken;

}
