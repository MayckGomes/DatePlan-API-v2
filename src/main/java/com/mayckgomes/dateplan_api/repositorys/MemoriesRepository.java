package com.mayckgomes.dateplan_api.repositorys;

import com.mayckgomes.dateplan_api.entitys.MemoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoriesRepository extends JpaRepository<MemoriesEntity, Long> {

    List<MemoriesEntity> findAllByRelationshipId(Long relationshipId);

    void deleteAllByRelationshipId(Long relationshipId);
    
}
