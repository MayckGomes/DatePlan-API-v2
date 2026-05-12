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
    private Long reciver_id;
    private String reciver_name;
    private Long sender_id;
    private String sender_name;

    public InviteResponse toInviteResponse(){
        return new InviteResponse(id, reciver_id, reciver_name, sender_id, sender_name);
    }

}
