package com.mayckgomes.dateplan_api.dto.invite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AcceptInviteResponse {

    private Long relationshipId;
    private String accessToken;

}
