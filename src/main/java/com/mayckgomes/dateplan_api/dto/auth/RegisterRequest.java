package com.mayckgomes.dateplan_api.dto.auth;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @Size(min = 1, max = 50)
    private String name;
    @Size(min = 1, max = 50)
    private String email;
    @Size(max = 250)
    private String password;

}
