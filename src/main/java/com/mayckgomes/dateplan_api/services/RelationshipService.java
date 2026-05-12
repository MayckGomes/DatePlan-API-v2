package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.dto.relationship.RelationshipResponse;
import com.mayckgomes.dateplan_api.exception.custom.relationship.RelationshipNotFoundException;
import com.mayckgomes.dateplan_api.exception.custom.relationship.UserDontHaveRelationshipException;
import com.mayckgomes.dateplan_api.exception.custom.relationship.UserRelationshipNotFoundException;
import com.mayckgomes.dateplan_api.repositorys.RelationshipRepository;
import com.mayckgomes.dateplan_api.repositorys.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RelationshipService {

    UserService userService;
    RelationshipRepository relationshipRepository;
    UserRepository userRepository;

    public RelationshipService(
            RelationshipRepository relationshipRepository,
            UserRepository userRepository,
            UserService userService
    ) {
        this.relationshipRepository = relationshipRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public RelationshipResponse getRelationshipById(Long id){

        if (id == null){
            throw new UserDontHaveRelationshipException();

        }

        var targetRelationship = relationshipRepository.findById(id).orElseThrow(RelationshipNotFoundException::new);
        var userId1 = userRepository.findById(targetRelationship.getUserId1()).orElseThrow(() -> new UserRelationshipNotFoundException("1"));
        var userId2 = userRepository.findById(targetRelationship.getUserId2()).orElseThrow(() -> new UserRelationshipNotFoundException("2"));

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
    public void deleteRelationshipById(Long id){

        if (id == null){
            throw new UserDontHaveRelationshipException();
        }

        var targetRelationship = relationshipRepository.findById(id).orElseThrow(RelationshipNotFoundException::new);

        var user1 = userRepository.findById(targetRelationship.getUserId1()).orElseThrow(() -> new UserRelationshipNotFoundException("1"));
        var user2 = userRepository.findById(targetRelationship.getUserId2()).orElseThrow(() -> new UserRelationshipNotFoundException("2"));

        user1.setRelationshipId(null);
        user2.setRelationshipId(null);

        userRepository.save(user1);
        userRepository.save(user2);

        relationshipRepository.deleteById(targetRelationship.getId());

        // TODO deletar compromissos
        // TODO deletar memorias

    }

    @Transactional
    public void changeInitialDay(Long relationshipId, String newDate){
        if (relationshipId == null){
            throw new UserDontHaveRelationshipException();
        }

        var targetRelationship = relationshipRepository.findById(relationshipId).orElseThrow(RelationshipNotFoundException::new);

        targetRelationship.setInitialDay(newDate);

        relationshipRepository.save(targetRelationship);
    }

}
