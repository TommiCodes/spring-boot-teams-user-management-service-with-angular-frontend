package com.usermanagement.repository;

import com.usermanagement.model.JoinRequest;
import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.model.enums.JoinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {

    boolean existsByJoinStatusAndTeamAndUser(JoinStatus joinStatus, Team team, User user);

    Page<JoinRequest> findAllByTeam(Team team, Pageable pageable);

}
