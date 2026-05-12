package com.mayckgomes.dateplan_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @Size(max = 50)
    @NotNull
    @NotEmpty
    private String name;

    @Email
    @Size(max = 50)
    @NotNull
    @NotEmpty
    private String email;

    @Size(max = 250)
    @NotNull
    @NotEmpty
    private String password;

}
