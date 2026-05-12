package com.mayckgomes.dateplan_api.entitys;

import com.mayckgomes.dateplan_api.dto.invite.InviteResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "invite")
@Table(name = "invite")
@NoArgsConstructor
@Getter
@Setter
public class InviteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long toId;
    private String toName;
    private Long fromId;
    private String fromName;

    public InviteResponse toInviteResponse(){
        return new InviteResponse(id, toId, toName, fromId, fromName);
    }

}
