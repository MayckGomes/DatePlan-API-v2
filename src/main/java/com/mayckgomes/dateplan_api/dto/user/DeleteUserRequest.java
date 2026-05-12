package com.mayckgomes.dateplan_api.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DeleteUserRequest {

    @NotNull
    @NotEmpty
    private Long id;

    @NotNull
    @NotEmpty
    private String refreshToken;

}
