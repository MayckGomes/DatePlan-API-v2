package com.mayckgomes.dateplan_api.dto.relationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RelationshipResponse {

    private Long id;
    private Long userId1;
    private String nameUser1;
    private Long userId2;
    private String nameUser2;
    private String initialDay;

}
