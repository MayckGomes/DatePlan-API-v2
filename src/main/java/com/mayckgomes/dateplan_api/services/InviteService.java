package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.dto.invite.*;
import com.mayckgomes.dateplan_api.entitys.InvitesEntity;
import com.mayckgomes.dateplan_api.entitys.RelationshipsEntity;
import com.mayckgomes.dateplan_api.exception.custom.invite.InviteNotFoundException;
import com.mayckgomes.dateplan_api.exception.custom.invite.NotDecisionMakerException;
import com.mayckgomes.dateplan_api.exception.custom.invite.UserHaveARelationshipException;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import com.mayckgomes.dateplan_api.jwt.JwtService;
import com.mayckgomes.dateplan_api.repositorys.InvitesRepository;
import com.mayckgomes.dateplan_api.repositorys.RelationshipsRepository;
import com.mayckgomes.dateplan_api.repositorys.UsersRepository;
import com.mayckgomes.dateplan_api.utils.SendNotification;
import com.mayckgomes.dateplan_api.utils.VerifyTokenText;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InviteService {

    InvitesRepository invitesRepository;
    UsersRepository usersRepository;
    RelationshipsRepository relationshipsRepository;
    JwtService jwtService;
    RedisBlackListService redisBlackListService;

    public InviteService(InvitesRepository invitesRepository,
                         UsersRepository usersRepository,
                         RelationshipsRepository relationshipsRepository,
                         JwtService jwtService,
                         RedisBlackListService redisBlackListService) {
        this.invitesRepository = invitesRepository;
        this.usersRepository = usersRepository;
        this.relationshipsRepository = relationshipsRepository;
        this.jwtService = jwtService;
        this.redisBlackListService = redisBlackListService;
    }

    @Transactional
    public CreateInviteResponse createInvite(CreateInviteRequest createInviteRequest) {

        var reciverUser = usersRepository.findByPublicId(createInviteRequest.getPublicIdTo())
                .orElseThrow(() -> new UserNotFoundException("Reciver"));

        if (reciverUser.getRelationshipId() != null){
            throw new UserHaveARelationshipException("Reciver");
        }

        var senderUser = usersRepository.findByPublicId(createInviteRequest.getPublicIdFrom())
                .orElseThrow(() -> new UserNotFoundException("Sender"));

        if (senderUser.getRelationshipId() != null){
            throw new UserHaveARelationshipException("Sender");
        }

        var inviteEntity = new InvitesEntity();

        inviteEntity.setSenderId(senderUser.getId());
        inviteEntity.setSenderName(senderUser.getName());

        inviteEntity.setReciverId(reciverUser.getId());
        inviteEntity.setReciverName(reciverUser.getName());
        inviteEntity.setIdDecisionMaker(reciverUser.getId());

        var inviteId = invitesRepository.save(inviteEntity).getId();

        SendNotification.sendInvite(reciverUser.getNotificationToken());

        return new CreateInviteResponse(inviteId);

    }

    public List<InviteResponse> getAllInvitesByToId(Long reciverId) {

        usersRepository.findById(reciverId).orElseThrow(UserNotFoundException::new);

        return invitesRepository.findAllByReciverId(reciverId).stream().map(InvitesEntity::toInviteResponse).toList();

    }

    @Transactional
    public AcceptInviteResponse acceptInvite(String accessToken, Long userId, AcceptInviteRequest invite) {

        accessToken = VerifyTokenText.verifyTokenText(accessToken);

        var targetInvite = invitesRepository.findById(invite.getInviteId())
                .orElseThrow(InviteNotFoundException::new);

        if (!targetInvite.getIdDecisionMaker().equals(userId)) {
            throw new NotDecisionMakerException();
        }

        var reciverUser = usersRepository.findById(targetInvite.getReciverId())
                .orElseThrow(() -> new UserNotFoundException("Reciver"));

        if (reciverUser.getRelationshipId() != null){

            invitesRepository.delete(targetInvite);

            throw new UserHaveARelationshipException("Reciver");
        }

        var senderUser = usersRepository.findById(targetInvite.getSenderId())
                .orElseThrow(() -> new UserNotFoundException("Sender"));

        if (senderUser.getRelationshipId() != null){

            invitesRepository.delete(targetInvite);

            throw new UserHaveARelationshipException("Sender");
        }

        var relationshipEntity = new RelationshipsEntity();

        relationshipEntity.setUserId1(targetInvite.getSenderId());
        relationshipEntity.setUserId2(targetInvite.getReciverId());
        relationshipEntity.setInitialDay(LocalDate.now().toString());

        var relationshipId = relationshipsRepository.save(relationshipEntity).getId();

        reciverUser.setRelationshipId(relationshipId);
        senderUser.setRelationshipId(relationshipId);

        usersRepository.save(reciverUser);
        usersRepository.save(senderUser);

        invitesRepository.delete(targetInvite);

        redisBlackListService.addAccessToken(jwtService.getTokenId(accessToken), accessToken);

        var newAccessToken = jwtService.createAccessToken(reciverUser.toUserDomain());

        SendNotification.sendRefresh(senderUser.getNotificationToken());

        return new AcceptInviteResponse(relationshipId, newAccessToken);

    }

    public void declineInvite(Long userId, DeclineInviteRequest declineInviteRequest) {

        var targetInvite = invitesRepository.findById(declineInviteRequest.getInviteId()).orElseThrow(InviteNotFoundException::new);

        if (!targetInvite.getIdDecisionMaker().equals(userId)) {
            throw new NotDecisionMakerException();
        }

        invitesRepository.delete(targetInvite);

    }

}
