package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.dto.date.CreateDateRequest;
import com.mayckgomes.dateplan_api.dto.date.DateResponse;
import com.mayckgomes.dateplan_api.dto.date.EditDateRequest;
import com.mayckgomes.dateplan_api.dto.memories.MemoryResponse;
import com.mayckgomes.dateplan_api.entitys.DatesEntity;
import com.mayckgomes.dateplan_api.entitys.MemoriesEntity;
import com.mayckgomes.dateplan_api.exception.custom.date.DateNotFoundException;
import com.mayckgomes.dateplan_api.exception.custom.relationship.RelationshipNotFoundException;
import com.mayckgomes.dateplan_api.exception.custom.user.UserNotFoundException;
import com.mayckgomes.dateplan_api.repositorys.DatesRepository;
import com.mayckgomes.dateplan_api.repositorys.MemoriesRepository;
import com.mayckgomes.dateplan_api.repositorys.RelationshipsRepository;
import com.mayckgomes.dateplan_api.repositorys.UsersRepository;
import com.mayckgomes.dateplan_api.utils.SendNotification;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateService {

    DatesRepository datesRepository;
    RelationshipsRepository relationshipsRepository;
    UsersRepository usersRepository;
    MemoriesRepository memoriesRepository;

    public DateService(DatesRepository datesRepository,
                       RelationshipsRepository relationshipsRepository,
                       UsersRepository usersRepository,
                       MemoriesRepository memoriesRepository) {
        this.datesRepository = datesRepository;
        this.relationshipsRepository = relationshipsRepository;
        this.usersRepository = usersRepository;
        this.memoriesRepository = memoriesRepository;
    }

    public List<DateResponse> getDates(Long relationshipId){

        if (relationshipId == null){
            throw new RelationshipNotFoundException();
        }

        var existsRelationship = relationshipsRepository.existsById(relationshipId);

        if (!existsRelationship){
            throw new RelationshipNotFoundException();
        }

        return datesRepository.findAllByRelationshipId(relationshipId)
                .stream()
                .map(DatesEntity::toDateResponse)
                .toList();

    }

    public DateResponse createDate(UserDomain user, CreateDateRequest dateRequest){

        var targetRelationship = relationshipsRepository.findById(dateRequest.getRelationshipId())
                .orElseThrow(RelationshipNotFoundException::new);


        var savedDate = datesRepository.save(dateRequest.toDatesEntity());

        UserDomain targetUser;

        if (targetRelationship.getUserId1().equals(user.getId())){

            targetUser = usersRepository.findById(targetRelationship.getUserId2())
                    .orElseThrow(UserNotFoundException::new)
                    .toUserDomain();

        } else {

            targetUser = usersRepository.findById(targetRelationship.getUserId1())
                    .orElseThrow(UserNotFoundException::new)
                    .toUserDomain();

        }

        SendNotification.sendNewDate(targetUser.getNotificationToken(), savedDate.toDateResponse());

        return savedDate.toDateResponse();

    }

    public DateResponse editDate(UserDomain user,EditDateRequest dateRequest){

        var targetRelationship = relationshipsRepository.findById(dateRequest.getRelationshipId())
                .orElseThrow(RelationshipNotFoundException::new);

        var existsDate = datesRepository.existsById(dateRequest.getId());

        if(!existsDate){
            throw new DateNotFoundException();
        }

        var savedDate = datesRepository.save(dateRequest.toDatesEntity());

        UserDomain targetUser;

        if (targetRelationship.getUserId1().equals(user.getId())){

            targetUser = usersRepository.findById(targetRelationship.getUserId2())
                    .orElseThrow(UserNotFoundException::new)
                    .toUserDomain();

        } else {

            targetUser = usersRepository.findById(targetRelationship.getUserId1())
                    .orElseThrow(UserNotFoundException::new)
                    .toUserDomain();

        }

        SendNotification.sendEditDate(targetUser.getNotificationToken(), savedDate.toDateResponse());

        return savedDate.toDateResponse();

    }

    public void deleteDate(UserDomain user, Long dateId){

        var targetDate = datesRepository.findById(dateId)
                .orElseThrow(DateNotFoundException::new);

        var targetRelationship = relationshipsRepository.findById(targetDate.getRelationshipId())
                .orElseThrow(RelationshipNotFoundException::new);

        UserDomain targetUser;

        if (targetRelationship.getUserId1().equals(user.getId())){

            targetUser = usersRepository.findById(targetRelationship.getUserId2())
                    .orElseThrow(UserNotFoundException::new)
                    .toUserDomain();

        } else {

            targetUser = usersRepository.findById(targetRelationship.getUserId1())
                    .orElseThrow(UserNotFoundException::new)
                    .toUserDomain();

        }

        SendNotification.sendDelete(targetUser.getNotificationToken(), targetDate.toDateResponse());

        datesRepository.deleteById(dateId);

    }

    @Transactional
    public MemoryResponse convertToMemory(Long dateId, Long userId){

        var targetDate = datesRepository.findById(dateId)
                .orElseThrow(DateNotFoundException::new);

        var newMemory = MemoriesEntity.builder()
                .date(targetDate.getDate())
                .local(targetDate.getLocal())
                .title(targetDate.getTitle())
                .description(targetDate.getDescription())
                .iconId(targetDate.getIconId())
                .relationshipId(targetDate.getRelationshipId())
                .authorId(userId)
                .build();

        var savedMemory = memoriesRepository.save(newMemory);

        datesRepository.deleteById(targetDate.getId());

        return savedMemory.toMemoryResponse();

    }

}
