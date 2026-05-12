package com.mayckgomes.dateplan_api.dto.relationship;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChangeInitialDayRequest {

    @NotNull
    @NotEmpty
    private String initialDay;

}
