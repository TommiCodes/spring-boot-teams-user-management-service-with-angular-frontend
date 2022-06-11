package com.usermanagement.service;

import com.usermanagement.model.*;
import com.usermanagement.model.enums.JoinStatus;
import com.usermanagement.model.enums.Roles;
import com.usermanagement.repository.JoinRequestRepository;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.model.requests.UpdateJoinTeamRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Objects;

@AllArgsConstructor
@Service
@Transactional
public class JoinRequestService {

    // Services
    private final UserService userService;
    private final TeamService teamService;
    // Repositories
    private final RoleRepository roleRepository;
    private final JoinRequestRepository joinRequestRepository;

    // save a joinrequest from a user to a team to the database
    public void save(Long teamId, Long userId) {
        User user = userService.findById(userId);
        Team team = teamService.findById(teamId);

        // build join Request
        JoinRequest joinRequest = JoinRequest.builder()
                .joinStatus(JoinStatus.INQUIRY)
                .team(team)
                .user(user)
                .build();

        // Check if user already in team
        if (user.getTeams().stream().anyMatch(ut -> Objects.equals(ut.getTeam().getId(), team.getId()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already in team");
        }

        // check for open requests to same team from user
        if (joinRequest.getJoinStatus() == JoinStatus.INQUIRY) {
            if (joinRequestRepository.existsByJoinStatusAndTeamAndUser(joinRequest.getJoinStatus(), joinRequest.getTeam(), joinRequest.getUser())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Open Request to this team already exists");
            }
        }

        joinRequestRepository.save(joinRequest);
    }

    // find all join Request for a team - paged
    public Page<JoinRequest> findJoinRequestsForTeam(Long teamId, Pageable pageable) {
        Team team = teamService.findById(teamId);
        return joinRequestRepository.findAllByTeam(team, pageable);
    }

    public JoinRequest findById(Long id) {
        return joinRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public boolean hasUserOpenJoinRequestForTeam(Long teamId, Long userId) {
        User user = userService.findById(userId);
        Team team = teamService.findById(teamId);

        return joinRequestRepository.existsByJoinStatusAndTeamAndUser(JoinStatus.INQUIRY, team, user);
    }

    // handle a join request
    public JoinRequest handle(Long id, UpdateJoinTeamRequest updateJoinTeamRequest) {
        // first find joinRequest
        JoinRequest joinRequest = findById(id);

        // if joinRequest Status in Database is not INQUIRY, then throw an Exc
        if (joinRequest.getJoinStatus() != JoinStatus.INQUIRY) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Join Request alrady accepted/declined.");
        }

        // if updateJoinRequest is DECLINED, then set the joinRequest in the database to DECLINED
        if(updateJoinTeamRequest.getJoinStatus() == JoinStatus.DECLINED) {
            joinRequest.setJoinStatus(updateJoinTeamRequest.getJoinStatus());
        }

        if(updateJoinTeamRequest.getJoinStatus() == JoinStatus.ACCEPTED) {
            // add the user to the team
            userService.addTeamToUser(joinRequest.getTeam().getId(), joinRequest.getUser().getId());

            // update joinStatus of the joinRequest
            joinRequest.setJoinStatus(updateJoinTeamRequest.getJoinStatus());
        }

        // save the updated join request to the db
        return joinRequestRepository.save(joinRequest);
    }

}
