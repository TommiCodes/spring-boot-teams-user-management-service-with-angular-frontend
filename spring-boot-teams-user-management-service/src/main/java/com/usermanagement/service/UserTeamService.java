package com.usermanagement.service;

import com.usermanagement.model.*;
import com.usermanagement.repository.UserTeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserTeamService {

    private final UserTeamRepository userTeamRepository;

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

    public Page<UserTeam> findAllByUserId(Long userId, Pageable pageable) {
        return userTeamRepository.findAllByUserId(userId, pageable);
    }

    public Page<UserTeam> findAllByTeamId(Long teamId, Pageable pageable) {
        return userTeamRepository.findAllByTeamId(teamId, pageable);
    }

    public UserTeam getById(UserTeamKey userTeamKey) {
        return userTeamRepository.getById(userTeamKey);
    }

    public void save(UserTeam userTeam) {
        this.userTeamRepository.save(userTeam);
    }

    public void delete(UserTeam userTeam) {
        this.userTeamRepository.delete(userTeam);
    }

}
