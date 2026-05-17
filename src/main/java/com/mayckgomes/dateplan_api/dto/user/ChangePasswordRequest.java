package com.mayckgomes.dateplan_api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChangePasswordRequest {

    @NotNull
    @NotBlank
    private String newPassword;

    @NotNull
    @NotBlank
    private String refreshToken;

}
