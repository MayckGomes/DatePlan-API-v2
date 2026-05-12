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
    private String nameUserId1;
    private Long userId2;
    private String nameUserId2;
    private String initialDay;

}
