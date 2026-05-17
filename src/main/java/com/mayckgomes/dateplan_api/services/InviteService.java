package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.dto.invite.*;
import com.mayckgomes.dateplan_api.entitys.InviteEntity;
import com.mayckgomes.dateplan_api.entitys.RelationshipEntity;
import com.mayckgomes.dateplan_api.exception.custom.invite.InviteNotExistsException;
import com.mayckgomes.dateplan_api.exception.custom.invite.NotDecisionMakerException;
import com.mayckgomes.dateplan_api.exception.custom.invite.UserHaveARelationshipException;
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
            throw new UserHaveARelationshipException("Reciver");
        }

        var senderUser = userRepository.findByPublicId(createInviteRequest.getPublicIdFrom())
                .orElseThrow(() -> new UserNotFoundException("Sender"));

        if (senderUser.getRelationshipId() != null){
            throw new UserHaveARelationshipException("Sender");
        }

        var inviteEntity = new InviteEntity();

        inviteEntity.setSenderId(senderUser.getId());
        inviteEntity.setSenderName(senderUser.getName());

        inviteEntity.setReciverId(reciverUser.getId());
        inviteEntity.setReciverName(reciverUser.getName());
        inviteEntity.setIdDecisionMaker(reciverUser.getId());

        var inviteId = inviteRepository.save(inviteEntity).getId();

        return new CreateInviteResponse(inviteId);

    }

    public List<InviteResponse> getAllInvitesByToId(Long reciverId) {

        userRepository.findById(reciverId).orElseThrow(UserNotFoundException::new);

        return inviteRepository.findAllByReciverId(reciverId).stream().map(InviteEntity::toInviteResponse).toList();

    }

    @Transactional
    public AcceptInviteResponse acceptInvite(Long userId,AcceptInviteRequest invite) {

        var targetInvite = inviteRepository.findById(invite.getInviteId()).orElseThrow(InviteNotExistsException::new);

        if (!targetInvite.getIdDecisionMaker().equals(userId)) {
            throw new NotDecisionMakerException();
        }

        var reciverUser = userRepository.findById(targetInvite.getReciverId())
                .orElseThrow(() -> new UserNotFoundException("Reciver"));

        if (reciverUser.getRelationshipId() != null){

            inviteRepository.delete(targetInvite);

            throw new UserHaveARelationshipException("Reciver");
        }

        var senderUser = userRepository.findById(targetInvite.getSenderId())
                .orElseThrow(() -> new UserNotFoundException("Sender"));

        if (senderUser.getRelationshipId() != null){

            inviteRepository.delete(targetInvite);

            throw new UserHaveARelationshipException("Sender");
        }

        var relationshipEntity = new RelationshipEntity();

        relationshipEntity.setUserId1(targetInvite.getSenderId());
        relationshipEntity.setUserId2(targetInvite.getReciverId());
        relationshipEntity.setInitialDay(LocalDate.now().toString());

        var relationshipId = relationshipRepository.save(relationshipEntity).getId();

        reciverUser.setRelationshipId(relationshipId);
        senderUser.setRelationshipId(relationshipId);

        userRepository.save(reciverUser);
        userRepository.save(senderUser);

        inviteRepository.delete(targetInvite);

        return new AcceptInviteResponse(relationshipId);

    }

    public void declineInvite(Long userId, DeclineInviteRequest declineInviteRequest) {

        var targetInvite = inviteRepository.findById(declineInviteRequest.getInviteId()).orElseThrow(InviteNotExistsException::new);

        if (!targetInvite.getIdDecisionMaker().equals(userId)) {
            throw new NotDecisionMakerException();
        }

        inviteRepository.delete(targetInvite);

    }

}
