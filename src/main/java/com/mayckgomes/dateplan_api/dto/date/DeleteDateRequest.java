package com.mayckgomes.dateplan_api.dto.date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DeleteDateRequest {

    @NotNull
    private Long dateId;

}
