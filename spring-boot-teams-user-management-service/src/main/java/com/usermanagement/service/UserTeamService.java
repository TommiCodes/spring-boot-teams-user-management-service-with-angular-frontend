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
public class UserTeamService {
    // Repositories
    private final UserTeamRepository userTeamRepository;

    // find all UserTeams Relations by user.id - paged
    public Page<UserTeam> findAllByUserId(Long userId, Pageable pageable) {
        return userTeamRepository.findAllByUserId(userId, pageable);
    }

    // find all UserTeams Relations by team.id - paged
    public Page<UserTeam> findAllByTeamId(Long teamId, Pageable pageable) {
        return userTeamRepository.findAllByTeamId(teamId, pageable);
    }

    // get a relationship by UserTeamKey (user.id & team.id)
    public UserTeam findById(UserTeamKey userTeamKey) {
        return userTeamRepository.findById(userTeamKey).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }

    // save a user to a team
    public void save(UserTeam userTeam) {
        this.userTeamRepository.save(userTeam);
    }

    // remove a user from a team
    public void delete(UserTeam userTeam) {
        this.userTeamRepository.delete(userTeam);
    }

    // helper to create a UserTeam
    public UserTeam build(User user, Team team, Role role) {
        UserTeamKey userTeamKey = UserTeamKey.builder()
                .userId(user.getId())
                .teamId(team.getId())
                .build();

        return UserTeam.builder()
                .id(userTeamKey)
                .user(user)
                .team(team)
                .role(role)
                .build();
    }

}
