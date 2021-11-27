package com.usermanagement.service;

import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.model.UserTeam;
import com.usermanagement.model.UserTeamKey;
import com.usermanagement.repository.UserTeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class UserTeamService {

    private final UserTeamRepository userTeamRepository;
    private final UserService userService;
    private final TeamService teamService;

    public void addUserToTeam(Long teamId, Long userId) {
        Team team = teamService.get(teamId);
        User user = userService.get(userId);

        UserTeamKey userTeamKey = UserTeamKey.builder()
                .teamId(team.getId())
                .userId(user.getId())
                .build();

        UserTeam userTeam = UserTeam.builder()
                .id(userTeamKey)
                .teams(team)
                .users(user)
                .build();

        userTeamRepository.save(userTeam);
    }

    public void removeUserFromTeam(Long teamId, Long userId) {
        Team team = teamService.get(teamId);
        User user = userService.get(userId);

        UserTeamKey userTeamKey = UserTeamKey.builder()
                .teamId(team.getId())
                .userId(user.getId())
                .build();

        UserTeam userTeam = UserTeam.builder()
                .id(userTeamKey)
                .build();

        userTeamRepository.delete(userTeam);
    }

    public void getTeamsForUser(Long userId) {
/*        User user = userService.get(userId);
        Set<UserTeam> userTeam = userTeamRepository.findByUsersId(user.getId());

        System.out.println("ASD");*/
    }


}
