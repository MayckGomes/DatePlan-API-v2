package com.mayckgomes.dateplan_api.dto.invite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class InviteResponse {

    private Long id;
    private Long reciverId;
    private String reciverName;
    private Long senderId;
    private String senderName;

}
