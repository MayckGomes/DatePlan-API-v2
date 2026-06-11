package com.mayckgomes.dateplan_api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateTermsOfUse {

    @NotNull
    private Long version;

    @Size(max = 10)
    @NotNull
    @NotBlank
    private String acceptedDate;

}
