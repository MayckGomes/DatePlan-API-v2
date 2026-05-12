package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.dto.invite.*;
import com.mayckgomes.dateplan_api.entitys.InviteEntity;
import com.mayckgomes.dateplan_api.entitys.RelationshipEntity;
import com.mayckgomes.dateplan_api.exception.custom.invite.InviteNotExistsException;
import com.mayckgomes.dateplan_api.exception.custom.invite.ToUserAlreadyInRelationshipException;
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

        var toUser = userRepository.findByPublicId(createInviteRequest.getPublicIdTo())
                .orElseThrow(() -> new UserNotFoundException("To"));

        if (toUser.getRelationshipId() != null){
            throw new ToUserAlreadyInRelationshipException("To");
        }

        var fromUser = userRepository.findByPublicId(createInviteRequest.getPublicIdFrom())
                .orElseThrow(() -> new UserNotFoundException("From"));

        if (fromUser.getRelationshipId() != null){
            throw new ToUserAlreadyInRelationshipException("From");
        }

        var inviteEntity = new InviteEntity();

        inviteEntity.setFromId(fromUser.getId());
        inviteEntity.setFromName(fromUser.getName());

        inviteEntity.setToId(toUser.getId());
        inviteEntity.setToName(toUser.getName());

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

        var toUser = userRepository.findById(targetInvite.getToId())
                .orElseThrow(() -> new UserNotFoundException("To"));

        if (toUser.getRelationshipId() != null){

            inviteRepository.delete(targetInvite);

            throw new ToUserAlreadyInRelationshipException("To");
        }

        var fromUser = userRepository.findById(targetInvite.getFromId())
                .orElseThrow(() -> new UserNotFoundException("From"));

        if (fromUser.getRelationshipId() != null){

            inviteRepository.delete(targetInvite);

            throw new ToUserAlreadyInRelationshipException("From");
        }

        var relationshipEntity = new RelationshipEntity();

        relationshipEntity.setUserId1(targetInvite.getFromId());
        relationshipEntity.setUserId2(targetInvite.getToId());
        relationshipEntity.setInitialDay(LocalDate.now().toString());

        var relationshipId = relationshipRepository.save(relationshipEntity).getId();

        toUser.setRelationshipId(relationshipId);
        fromUser.setRelationshipId(relationshipId);

        userRepository.save(toUser);
        userRepository.save(fromUser);

        inviteRepository.delete(targetInvite);

        return new AcceptInviteResponse(relationshipId);

    }

    public void declineInvite(DeclineInviteRequest declineInviteRequest) {

        var targetInvite = inviteRepository.findById(declineInviteRequest.getInviteId()).orElseThrow(InviteNotExistsException::new);

        inviteRepository.delete(targetInvite);

    }

}
