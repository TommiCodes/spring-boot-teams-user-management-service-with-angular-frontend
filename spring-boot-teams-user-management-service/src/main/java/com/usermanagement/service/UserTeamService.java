package com.usermanagement.service;

import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.model.UserTeam;
import com.usermanagement.model.UserTeamKey;
import com.usermanagement.repository.UserTeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserTeamService {

    private final UserTeamRepository userTeamRepository;

    public UserTeam build(User user, Team team) {
        UserTeamKey userTeamKey = UserTeamKey.builder()
                .userId(user.getId())
                .teamId(team.getId())
                .build();

        return UserTeam.builder()
                .id(userTeamKey)
                .user(user)
                .team(team)
                .build();
    }

    public Page<UserTeam> findAllByUserId(Long userId, Pageable pageable) {
        return userTeamRepository.findAllByUserId(userId, pageable);
    }

    public Page<UserTeam> findAllByTeamId(Long teamId, Pageable pageable) {
        return userTeamRepository.findAllByTeamId(teamId, pageable);
    }

    public UserTeam save(UserTeam userTeam) {
        return this.userTeamRepository.save(userTeam);
    }

    public void delete(UserTeam userTeam) {
        this.userTeamRepository.delete(userTeam);
    }

}
