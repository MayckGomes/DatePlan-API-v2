package com.mayckgomes.dateplan_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @Size(min = 1, max = 50)
    @NotNull
    private String name;

    @Email
    @Size(min = 1, max = 50)
    @NotNull
    private String email;

    @Size(max = 250)
    @NotNull
    private String password;

}
