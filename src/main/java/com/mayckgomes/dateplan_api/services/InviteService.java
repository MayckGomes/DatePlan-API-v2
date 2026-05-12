package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.dto.invite.*;
import com.mayckgomes.dateplan_api.entitys.InviteEntity;
import com.mayckgomes.dateplan_api.entitys.RelationshipEntity;
import com.mayckgomes.dateplan_api.exception.custom.invite.InviteNotExistsException;
import com.mayckgomes.dateplan_api.exception.custom.invite.UserAlreadyInRelationshipException;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import com.mayckgomes.dateplan_api.repositorys.InviteRepository;
import com.mayckgomes.dateplan_api.repositorys.RelationshipRepository;
import com.mayckgomes.dateplan_api.repositorys.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InviteService {

    InviteRepository inviteRepository;
    UserRepository userRepository;
    RelationshipRepository relationshipRepository;

    public InviteService(InviteRepository inviteRepository,
                         UserRepository userRepository,
                         RelationshipRepository relationshipRepository) {
        this.inviteRepository = inviteRepository;
        this.userRepository = userRepository;
        this.relationshipRepository = relationshipRepository;
    }

    @Transactional
    public CreateInviteResponse createInvite(CreateInviteRequest createInviteRequest) {

        var reciverUser = userRepository.findByPublicId(createInviteRequest.getPublicIdTo())
                .orElseThrow(() -> new UserNotFoundException("Reciver"));

        if (reciverUser.getRelationshipId() != null){
            throw new UserAlreadyInRelationshipException("Reciver");
        }

        var senderUser = userRepository.findByPublicId(createInviteRequest.getPublicIdFrom())
                .orElseThrow(() -> new UserNotFoundException("Sender"));

        if (senderUser.getRelationshipId() != null){
            throw new UserAlreadyInRelationshipException("Sender");
        }

        var inviteEntity = new InviteEntity();

        inviteEntity.setSender_id(senderUser.getId());
        inviteEntity.setSender_name(senderUser.getName());

        inviteEntity.setReciver_id(reciverUser.getId());
        inviteEntity.setReciver_name(reciverUser.getName());

        var inviteId = inviteRepository.save(inviteEntity).getId();

        return new CreateInviteResponse(inviteId);

    }

    public List<InviteResponse> getAllInvitesByToId(Long toId) {

        userRepository.findById(toId).orElseThrow(UserNotFoundException::new);

        return inviteRepository.findAllByToId(toId).stream().map(InviteEntity::toInviteResponse).toList();

    }

    @Transactional
    public AcceptInviteResponse acceptInvite(AcceptInviteRequest invite) {

        var targetInvite = inviteRepository.findById(invite.getInviteId()).orElseThrow(InviteNotExistsException::new);

        var reciverUser = userRepository.findById(targetInvite.getReciver_id())
                .orElseThrow(() -> new UserNotFoundException("Reciver"));

        if (reciverUser.getRelationshipId() != null){

            inviteRepository.delete(targetInvite);

            throw new UserAlreadyInRelationshipException("Reciver");
        }

        var senderUser = userRepository.findById(targetInvite.getSender_id())
                .orElseThrow(() -> new UserNotFoundException("Sender"));

        if (senderUser.getRelationshipId() != null){

            inviteRepository.delete(targetInvite);

            throw new UserAlreadyInRelationshipException("Sender");
        }

        var relationshipEntity = new RelationshipEntity();

        relationshipEntity.setUser_id1(targetInvite.getSender_id());
        relationshipEntity.setUser_id2(targetInvite.getReciver_id());
        relationshipEntity.setInitial_day(LocalDate.now().toString());

        var relationshipId = relationshipRepository.save(relationshipEntity).getId();

        reciverUser.setRelationshipId(relationshipId);
        senderUser.setRelationshipId(relationshipId);

        userRepository.save(reciverUser);
        userRepository.save(senderUser);

        inviteRepository.delete(targetInvite);

        return new AcceptInviteResponse(relationshipId);

    }

    public void declineInvite(DeclineInviteRequest declineInviteRequest) {

        var targetInvite = inviteRepository.findById(declineInviteRequest.getInviteId()).orElseThrow(InviteNotExistsException::new);

        inviteRepository.delete(targetInvite);

    }

}
