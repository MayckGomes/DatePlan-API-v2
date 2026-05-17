package com.mayckgomes.dateplan_api.dto.invite;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateInviteRequest {

    @NotNull
    @NotBlank
    private String publicIdTo;

    @NotNull
    @NotBlank
    private String publicIdFrom;

}
