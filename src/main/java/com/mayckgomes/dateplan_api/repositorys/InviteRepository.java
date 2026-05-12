package com.mayckgomes.dateplan_api.repositorys;

import com.mayckgomes.dateplan_api.entitys.InviteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<InviteEntity,Long> {

    List<InviteEntity> findAllByToId(Long toId);

}
