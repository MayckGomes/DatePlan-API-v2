package com.mayckgomes.dateplan_api.dto.invite;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AcceptInviteRequest {

    @NotNull
    private Long inviteId;

}
