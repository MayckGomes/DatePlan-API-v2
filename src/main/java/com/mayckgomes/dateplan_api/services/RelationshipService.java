package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.dto.relationship.RelationshipResponse;
import com.mayckgomes.dateplan_api.exception.custom.relationship.RelationshipNotFoundException;
import com.mayckgomes.dateplan_api.exception.custom.relationship.UserDontHaveRelationshipException;
import com.mayckgomes.dateplan_api.exception.custom.relationship.UserRelationshipNotFoundException;
import com.mayckgomes.dateplan_api.jwt.JwtService;
import com.mayckgomes.dateplan_api.repositorys.DatesRepository;
import com.mayckgomes.dateplan_api.repositorys.MemoriesRepository;
import com.mayckgomes.dateplan_api.repositorys.RelationshipsRepository;
import com.mayckgomes.dateplan_api.repositorys.UsersRepository;
import com.mayckgomes.dateplan_api.utils.SendNotification;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RelationshipService {

    UserService userService;
    RelationshipsRepository relationshipsRepository;
    DatesRepository datesRepository;
    MemoriesRepository memoriesRepository;
    UsersRepository usersRepository;
    JwtService jwtService;
    RedisBlackListService redisBlackListService;

    public RelationshipService(
            RelationshipsRepository relationshipsRepository,
            DatesRepository datesRepository,
            MemoriesRepository memoriesRepository,
            UsersRepository usersRepository,
            UserService userService,
            JwtService jwtService,
            RedisBlackListService redisBlackListService
    ) {
        this.relationshipsRepository = relationshipsRepository;
        this.datesRepository = datesRepository;
        this.memoriesRepository = memoriesRepository;
        this.usersRepository = usersRepository;
        this.userService = userService;
        this.jwtService = jwtService;
        this.redisBlackListService = redisBlackListService;
    }

    public RelationshipResponse getRelationshipById(Long id){

        if (id == null){
            throw new UserDontHaveRelationshipException();

        }

        var targetRelationship = relationshipsRepository.findById(id).orElseThrow(RelationshipNotFoundException::new);
        var userId1 = usersRepository.findById(targetRelationship.getUserId1()).orElseThrow(() -> new UserRelationshipNotFoundException("1"));
        var userId2 = usersRepository.findById(targetRelationship.getUserId2()).orElseThrow(() -> new UserRelationshipNotFoundException("2"));

        return new RelationshipResponse(
                targetRelationship.getId(),
                userId1.getId(),
                userId1.getName(),
                userId2.getId(),
                userId2.getName(),
                targetRelationship.getInitialDay()
                );
    }
    @Transactional
    public void deleteRelationshipById(UserDomain user){

        if (user.getRelationshipId() == null){
            throw new UserDontHaveRelationshipException();
        }

        var targetRelationship = relationshipsRepository.findById(user.getRelationshipId()).orElseThrow(RelationshipNotFoundException::new);

        var user1 = usersRepository.findById(targetRelationship.getUserId1())
                .orElseThrow(() -> new UserRelationshipNotFoundException("1"));

        var user2 = usersRepository.findById(targetRelationship.getUserId2())
                .orElseThrow(() -> new UserRelationshipNotFoundException("2"));

        user1.setRelationshipId(null);
        user2.setRelationshipId(null);

        usersRepository.save(user1);
        usersRepository.save(user2);

        datesRepository.deleteAllByRelationshipId(targetRelationship.getId());

        memoriesRepository.deleteAllByRelationshipId(targetRelationship.getId());

        relationshipsRepository.deleteById(targetRelationship.getId());

        SendNotification.sendRefresh(user1.getNotificationToken());
        SendNotification.sendRefresh(user2.getNotificationToken());

        user.setRelationshipId(null);

    }

    @Transactional
    public void changeInitialDay(Long relationshipId, String newDate){
        if (relationshipId == null){
            throw new UserDontHaveRelationshipException();
        }

        var targetRelationship = relationshipsRepository.findById(relationshipId).orElseThrow(RelationshipNotFoundException::new);

        targetRelationship.setInitialDay(newDate);

        relationshipsRepository.save(targetRelationship);
    }

}
