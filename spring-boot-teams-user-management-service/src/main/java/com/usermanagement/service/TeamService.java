package com.usermanagement.service;

import com.usermanagement.model.*;
import com.usermanagement.model.enums.Roles;
import com.usermanagement.model.specs.TeamSpecs;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.repository.TeamRepository;
import com.usermanagement.model.requests.CreateTeamRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class TeamService {

    // Services
    private final UserTeamRelationService userTeamRelationService;
    // Repositories
    private final TeamRepository teamRepository;
    private final RoleRepository roleRepository;

    public void create(CreateTeamRequest createTeamRequest, User admin) {
        Team team = teamRepository.save(Team.builder()
                        .name(createTeamRequest.getName())
                        .build());

        // Since the Creator of the Team is the owner he gets the admin role
        Role role = roleRepository.findByRole(Roles.ADMIN).orElseThrow(() -> new RuntimeException("Not found"));
        UserTeamRelation userTeamRelation = userTeamRelationService.build(admin, team, role);
        userTeamRelationService.save(userTeamRelation);
    }

    public UserTeamRelation updateRoleOfUser(Long teamId, Long userId, Roles r) {
        UserTeamRelationKey userTeamRelationKey = UserTeamRelationKey.builder()
                .userId(userId)
                .teamId(teamId)
                .build();

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Not found"));

        UserTeamRelation userTeamRelation = userTeamRelationService.findById(userTeamRelationKey);

        // Check that at least one ADMIN is present
        List<UserTeamRelation> adminList = team.getUsers().stream()
                .filter(val -> val.getRole().getRole().equals(Roles.ADMIN))
                .toList();

        if (adminList.size() == 1 && adminList.contains(userTeamRelation)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Last Admin in Team. Role can not be changed");
        }

        // update role of user
        Role role = roleRepository.findByRole(r).orElseThrow();
        userTeamRelation.setRole(role);

        return userTeamRelationService.save(userTeamRelation);
    }

    public Page<Team> search(Pageable pageable, String name) {
        return teamRepository.findAll(name != null ? TeamSpecs.nameLike(name) : null, pageable);
    }

    // find a team by id
    public Team findById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }


    // find All Users for a team by team.id - paged
    public Page<UserTeamRelation> findAllUsersForTeam(Long teamId, Pageable pageable) {
        Team team = findById(teamId);

        // find the paged UserTeams
        Page<UserTeamRelation> userTeamPage = userTeamRelationService.findAllByTeamId(team.getId(), pageable);
        // get the pageable
        Pageable userTeamPageable = userTeamPage.getPageable();

        // make a Page<Team> and return it
        return userTeamPage;
    }

}
