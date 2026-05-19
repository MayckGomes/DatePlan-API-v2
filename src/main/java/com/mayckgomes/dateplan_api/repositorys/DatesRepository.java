package com.mayckgomes.dateplan_api.repositorys;

import com.mayckgomes.dateplan_api.entitys.DatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatesRepository extends JpaRepository<DatesEntity, Long> {

    List<DatesEntity> findAllByRelationshipId(Long relationshipId);

    Void deleteAllByRelationshipId(Long relationshipId);
}
