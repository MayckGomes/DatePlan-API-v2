package com.mayckgomes.dateplan_api.dto.memories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MemoryResponse {

    private Long id;
    private String date;
    private String local;
    private String title;
    private String description;
    private Long iconId;
    private Long relationshipId;
    private Long authorId;

}
