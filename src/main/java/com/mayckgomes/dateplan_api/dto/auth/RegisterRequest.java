package com.mayckgomes.dateplan_api.dto.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @Size(max = 50)
    @NotNull
    @NotBlank
    private String name;

    @Email
    @Size(max = 255)
    @NotNull
    @NotBlank
    private String email;

    @Size(max = 250)
    @NotNull
    @NotBlank
    private String password;

}
