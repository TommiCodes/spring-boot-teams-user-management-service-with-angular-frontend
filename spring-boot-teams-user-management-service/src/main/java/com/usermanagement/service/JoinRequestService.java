package com.usermanagement.service;

import com.usermanagement.model.*;
import com.usermanagement.model.enums.JoinStatus;
import com.usermanagement.repository.JoinRequestRepository;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.requests.UpdateJoinTeamRequest;
import lombok.AllArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class JoinRequestService {

    private final JoinRequestRepository joinRequestRepository;

    private final UserService userService;
    private final TeamService teamService;
    private final UserTeamService userTeamService;

    private final RoleRepository roleRepository;

    public JoinRequest save(Long teamId, Long userId) {
        User user = userService.get(userId);
        Team team = teamService.get(teamId);
        Role role = roleRepository.getById(1L);


        // build join Request
        JoinRequest joinRequest = JoinRequest.builder()
                .joinStatus(JoinStatus.INQUIRY)
                .team(team)
                .user(user)
                .build();

        // Check if user already in team
        UserTeam userTeam = userTeamService.build(joinRequest.getUser(), joinRequest.getTeam(), role);

        if (user.getTeams().contains(userTeam)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already in team");
        }

        // check for open requests to same team from user
        if (joinRequest.getJoinStatus() == JoinStatus.INQUIRY) {
            if (joinRequestRepository.existsByJoinStatusAndTeamAndUser(joinRequest.getJoinStatus(), joinRequest.getTeam(), joinRequest.getUser())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Open Request to this team already exists");
            }
        }

        return joinRequestRepository.save(joinRequest);
    }

    public Page<JoinRequest> findJoinRequestsForTeam(Long teamId, Pageable pageable) {
        Team team = teamService.get(teamId);
        return joinRequestRepository.findAllByTeam(team, pageable);
    }

    public void handle(Long id, UpdateJoinTeamRequest updateJoinTeamRequest) {
        JoinRequest joinRequest = joinRequestRepository.getById(id);

        if (joinRequest.getJoinStatus() != JoinStatus.INQUIRY) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Join Request alrady accepted/declined.");
        }

        joinRequest.setJoinStatus(updateJoinTeamRequest.getJoinStatus());
        joinRequestRepository.save(joinRequest);
    }

}
