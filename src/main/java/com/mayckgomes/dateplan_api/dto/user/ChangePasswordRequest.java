package com.mayckgomes.dateplan_api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChangePasswordRequest {

    private String newPassword;
    private String refreshToken;

}
