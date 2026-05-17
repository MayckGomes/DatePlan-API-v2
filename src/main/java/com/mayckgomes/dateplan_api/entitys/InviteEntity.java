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
    @Column(name = "reciver_id")
    private Long reciverId;

    @Column(name = "reciver_name")
    private String reciverName;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "decision_maker_id")
    private Long idDecisionMaker;

    public InviteResponse toInviteResponse(){
        return new InviteResponse(id, reciverId, reciverName, senderId, senderName);
    }

}
