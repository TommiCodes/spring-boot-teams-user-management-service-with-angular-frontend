package com.usermanagement.repository;

import com.usermanagement.model.UserTeam;
import com.usermanagement.model.UserTeamKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTeamRepository extends JpaRepository<UserTeam, UserTeamKey> {

    Page<UserTeam> findAllByUserId(Long userId, Pageable pageable);

    Page<UserTeam> findAllByTeamId(Long teamId, Pageable pageable);

}
