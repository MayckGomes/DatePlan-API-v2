package com.mayckgomes.dateplan_api.repositorys;

import com.mayckgomes.dateplan_api.entitys.DatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatesRepository extends JpaRepository<DatesEntity, Long> {
}
