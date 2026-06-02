package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.dto.memories.CreateMemoryRequest;
import com.mayckgomes.dateplan_api.dto.memories.EditMemoryRequest;
import com.mayckgomes.dateplan_api.dto.memories.MemoryResponse;
import com.mayckgomes.dateplan_api.entitys.MemoriesEntity;
import com.mayckgomes.dateplan_api.exception.custom.date.DateNotFoundException;
import com.mayckgomes.dateplan_api.exception.custom.memory.MemoryNotFoundException;
import com.mayckgomes.dateplan_api.exception.custom.relationship.RelationshipNotFoundException;
import com.mayckgomes.dateplan_api.repositorys.MemoriesRepository;
import com.mayckgomes.dateplan_api.repositorys.RelationshipsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoryService {

    RelationshipsRepository relationshipsRepository;
    MemoriesRepository memoriesRepository;

    public MemoryService(RelationshipsRepository relationshipsRepository,
                         MemoriesRepository memoriesRepository){
        this.relationshipsRepository = relationshipsRepository;
        this.memoriesRepository = memoriesRepository;
    }

    public List<MemoryResponse> getMemories(Long relationshipId){

        var existsRelationship = relationshipsRepository.existsById(relationshipId);

        if (!existsRelationship){
            throw new RelationshipNotFoundException();
        }

        return memoriesRepository.findAllByRelationshipId(relationshipId)
                .stream()
                .map(MemoriesEntity::toMemoryResponse)
                .toList();

    }

    public MemoryResponse createMemory(CreateMemoryRequest memoryRequest){

        var existsRelationship = relationshipsRepository.existsById(memoryRequest.getRelationshipId());

        if (!existsRelationship){
            throw new RelationshipNotFoundException();
        }

        var savedDate = memoriesRepository.save(memoryRequest.toMemoriesEntity());

        return savedDate.toMemoryResponse();

    }

    public MemoryResponse editMemory(EditMemoryRequest memoryRequest){

        var existsRelationship = relationshipsRepository.existsById(memoryRequest.getRelationshipId());

        if (!existsRelationship){
            throw new RelationshipNotFoundException();
        }

        var existsDate = memoriesRepository.existsById(memoryRequest.getId());

        if(!existsDate){
            throw new DateNotFoundException();
        }

        var savedDate = memoriesRepository.save(memoryRequest.toMemoriesEntity());

        return savedDate.toMemoryResponse();

    }

    public void deleteMemory(Long memoryId, Long relationshipId){

        var existsRelationship = relationshipsRepository.existsById(relationshipId);

        if (!existsRelationship){
            throw new RelationshipNotFoundException();
        }

        var existsDate = memoriesRepository.existsById(memoryId);

        if(!existsDate){
            throw new MemoryNotFoundException();
        }

        memoriesRepository.deleteById(memoryId);

    }

}
