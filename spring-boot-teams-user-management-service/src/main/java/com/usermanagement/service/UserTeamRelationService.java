package com.usermanagement.service;

import com.usermanagement.model.*;
import com.usermanagement.repository.UserTeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;


// Service that handles the many-to-many relationship between user and team
@AllArgsConstructor
@Service
@Transactional
public class UserTeamRelationService {

    // Repositories
    private final UserTeamRepository userTeamRepository;

    // find all UserTeams Relations by user.id - paged
    public Page<UserTeamRelation> findAllByUserId(Long userId, Pageable pageable) {
        return userTeamRepository.findAllByUserId(userId, pageable);
    }

    // find all UserTeams Relations by team.id - paged
    public Page<UserTeamRelation> findAllByTeamId(Long teamId, Pageable pageable) {
        return userTeamRepository.findAllByTeamId(teamId, pageable);
    }

    // get a relationship by UserTeamKey (user.id & team.id)
    public UserTeamRelation findById(UserTeamRelationKey userTeamRelationKey) {
        return userTeamRepository.findById(userTeamRelationKey).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }

    // save a user to a team
    public UserTeamRelation save(UserTeamRelation userTeamRelation) {
        return this.userTeamRepository.save(userTeamRelation);
    }

    // remove a user from a team
    public void delete(UserTeamRelation userTeamRelation) {
        this.userTeamRepository.delete(userTeamRelation);
    }

    // helper to create a UserTeam
    public UserTeamRelation build(User user, Team team, Role role) {
        UserTeamRelationKey userTeamRelationKey = UserTeamRelationKey.builder()
                .userId(user.getId())
                .teamId(team.getId())
                .build();

        return UserTeamRelation.builder()
                .id(userTeamRelationKey)
                .user(user)
                .team(team)
                .role(role)
                .build();
    }

}
