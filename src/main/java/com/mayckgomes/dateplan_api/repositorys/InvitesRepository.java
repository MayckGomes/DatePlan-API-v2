package com.mayckgomes.dateplan_api.repositorys;

import com.mayckgomes.dateplan_api.entitys.InvitesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitesRepository extends JpaRepository<InvitesEntity,Long> {

    List<InvitesEntity> findAllByReciverId(Long reciverId);

}
