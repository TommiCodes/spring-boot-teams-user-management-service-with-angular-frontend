package com.usermanagement.service;

import com.usermanagement.model.Role;
import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.model.UserTeam;
import com.usermanagement.model.specs.TeamSpecs;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.repository.TeamRepository;
import com.usermanagement.requests.CreateTeamRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class TeamService {
    // Services
    private final UserTeamService userTeamService;
    // Repositories
    private final TeamRepository teamRepository;
    private final RoleRepository roleRepository;

    public void create(CreateTeamRequest createTeamRequest, User admin) {
        Team team = teamRepository.save(Team.builder()
                        .name(createTeamRequest.getName())
                        .build());

        // Role id = 1 is always the ADMIN Role
        // Since the Creator of the Team is the owner he gets the admin role
        Role role = roleRepository.getById(1L);
        UserTeam userTeam = userTeamService.build(admin, team, role);
        userTeamService.save(userTeam);
    }

    // find all teams - paged
    // TODO: enrich with Specifications
    public Page<Team> findAll(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    public Page<Team> search(Pageable pageable, String name) {
        return teamRepository.findAll(name != null ? TeamSpecs.nameLike(name) : null, pageable);
    }

    // find a team by id
    public Team findById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Not fround"));
    }


    // find All Users for a team by team.id - paged
    public Page<UserTeam> findAllUsersForTeam(Long teamId, Pageable pageable) {
        Team team = findById(teamId);

        // find the paged UserTeams
        Page<UserTeam> userTeamPage = userTeamService.findAllByTeamId(team.getId(), pageable);
        // get the pageable
        Pageable userTeamPageable = userTeamPage.getPageable();

        // make a Page<Team> and return it
        return userTeamPage;
    }

}
