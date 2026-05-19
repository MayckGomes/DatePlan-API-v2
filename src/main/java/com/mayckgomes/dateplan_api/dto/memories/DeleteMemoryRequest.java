package com.mayckgomes.dateplan_api.dto.memories;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DeleteMemoryRequest {

    @NotNull
    private Long memoryId;

}
