package com.usermanagement.service;

import com.usermanagement.model.Team;
import com.usermanagement.repository.TeamRepository;
import com.usermanagement.requests.CreateTeamRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    public Team create(CreateTeamRequest createTeamRequest) {
        Team team = Team.builder()
                .name(createTeamRequest.getName())
                .build();

        return teamRepository.save(team);
    }

    public Team get(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Not fround"));
    }

}
