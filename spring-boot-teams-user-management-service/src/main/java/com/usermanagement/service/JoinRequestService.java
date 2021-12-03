package com.usermanagement.service;

import com.usermanagement.model.JoinRequest;
import com.usermanagement.model.Team;
import com.usermanagement.model.UserTeam;
import com.usermanagement.model.enums.JoinStatus;
import com.usermanagement.repository.JoinRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class JoinRequestService {

    private final JoinRequestRepository joinRequestRepository;
    private final UserTeamService userTeamService;

    public JoinRequest save(JoinRequest joinRequest) {
        // Check if user already in team
        UserTeam userTeam = userTeamService.build(joinRequest.getUser(), joinRequest.getTeam());

        if (joinRequest.getUser().getTeams().contains(userTeam)) {
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

    public Page<JoinRequest> findJoinRequestsForTeam(Team team, Pageable pageable) {
        return joinRequestRepository.findAllByTeam(team, pageable);
    }

}
