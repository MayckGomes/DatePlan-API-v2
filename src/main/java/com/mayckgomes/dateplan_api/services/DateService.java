package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.dto.date.CreateDateRequest;
import com.mayckgomes.dateplan_api.dto.date.DateResponse;
import com.mayckgomes.dateplan_api.dto.date.EditDateRequest;
import com.mayckgomes.dateplan_api.entitys.DatesEntity;
import com.mayckgomes.dateplan_api.exception.custom.date.DateNotFoundException;
import com.mayckgomes.dateplan_api.exception.custom.relationship.RelationshipNotFoundException;
import com.mayckgomes.dateplan_api.repositorys.DatesRepository;
import com.mayckgomes.dateplan_api.repositorys.RelationshipsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateService {

    DatesRepository datesRepository;
    RelationshipsRepository relationshipsRepository;

    public DateService(DatesRepository datesRepository, RelationshipsRepository relationshipsRepository)
    {
        this.datesRepository = datesRepository;
        this.relationshipsRepository = relationshipsRepository;
    }

    public List<DateResponse> getDates(Long relationshipId){

        var existsRelationship = relationshipsRepository.existsById(relationshipId);

        if (!existsRelationship){
            throw new RelationshipNotFoundException();
        }

        return datesRepository.findAllByRelationshipId(relationshipId)
                .stream()
                .map(DatesEntity::toDateResponse)
                .toList();

    }

    public DateResponse createDate(CreateDateRequest dateRequest){

        var existsRelationship = relationshipsRepository.existsById(dateRequest.getRelationshipId());

        if (!existsRelationship){
            throw new RelationshipNotFoundException();
        }

        var savedDate = datesRepository.save(dateRequest.toDatesEntity());

        return savedDate.toDateResponse();

    }

    public DateResponse editDate(EditDateRequest dateRequest){

        var existsRelationship = relationshipsRepository.existsById(dateRequest.getRelationshipId());

        if (!existsRelationship){
            throw new RelationshipNotFoundException();
        }

        var existsDate = datesRepository.existsById(dateRequest.getId());

        if(!existsDate){
            throw new DateNotFoundException();
        }

        var savedDate = datesRepository.save(dateRequest.toDatesEntity());

        return savedDate.toDateResponse();

    }

    public void deleteDate(Long dateId){

        var existsDate = datesRepository.existsById(dateId);

        if(!existsDate){
            throw new DateNotFoundException();
        }

        datesRepository.deleteById(dateId);

    }

}
