package com.usermanagement.repository;

import com.usermanagement.model.UserTeamRelation;
import com.usermanagement.model.UserTeamRelationKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeamRelation, UserTeamRelationKey> {

    Page<UserTeamRelation> findAllByUserId(Long userId, Pageable pageable);

    Page<UserTeamRelation> findAllByTeamId(Long teamId, Pageable pageable);

}
