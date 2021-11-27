package com.usermanagement.repository;

import com.usermanagement.model.UserTeam;
import com.usermanagement.model.UserTeamKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, UserTeamKey> {

    Set<UserTeam> findByUsersId(Long userId);

}
