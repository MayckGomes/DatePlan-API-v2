package com.mayckgomes.dateplan_api.dto.invite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class InviteResponse {

    private Long id;
    private Long toId;
    private String toName;
    private Long fromId;
    private String fromName;

}
