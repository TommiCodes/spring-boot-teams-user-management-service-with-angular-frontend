package com.usermanagement.controller;

import com.usermanagement.model.JoinRequest;
import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.requests.CreateJoinTeamRequest;
import com.usermanagement.requests.CreateTeamRequest;
import com.usermanagement.service.JoinRequestService;
import com.usermanagement.service.TeamService;
import com.usermanagement.service.UserService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@BasePathAwareController
public class TeamController {

    private final TeamService teamService;
    private final JoinRequestService joinRequestService;
    private final UserService userService;

    // TODO: enrich with Specifications
    @GetMapping("/teams")
    public ResponseEntity<?> findAllTeams(Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler, PersistentEntityResourceAssembler persistentEntityResourceAssembler) {
        Page<Team> teamPage = teamService.findAll(pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(teamPage, persistentEntityResourceAssembler));
    }

    @PostMapping("/teams")
    public ResponseEntity<?> createTeam(@RequestBody CreateTeamRequest createTeamRequest) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User admin = userService.get(Long.parseLong(userId, 10));
        teamService.create(createTeamRequest, admin);
        return ResponseEntity.ok("Created");
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, PersistentEntityResourceAssembler assembler) {
        Team team = teamService.get(id);
        return ResponseEntity.ok(assembler.toModel(team));
    }

    @GetMapping("/teams/{id}/users")
    public ResponseEntity<?> findAllUsersForTeam(@PathVariable Long id, Pageable pageable, PagedResourcesAssembler assembler) {
        Page<User> userPage = teamService.findAllUsersForTeam(id, pageable);
        return ResponseEntity.ok(assembler.toModel(userPage));
    }

    @PostMapping("/teams/{id}/send-join-request")
    public ResponseEntity<?> sendJoinRequestForTeam(@PathVariable Long id, @RequestBody CreateJoinTeamRequest createJoinTeamRequest) {
        joinRequestService.save(id, createJoinTeamRequest.getUserId());
        return ResponseEntity.ok("Join Request created");
    }

    // TODO: add Specifications to search for the JoinRequest Status
    // TODO: add Security so that only Team Admins can see the join requests for their team
    // If you want to inline the user & team to the joinRequest, add "?projection=details" to your request
    @GetMapping("/teams/{id}/join-requests")
    public ResponseEntity<?> findJoinRequestsForTeam(@PathVariable Long id, Pageable pageable, PagedResourcesAssembler assembler, PersistentEntityResourceAssembler persistentEntityResourceAssembler) {
        Page<JoinRequest> joinRequestPage = joinRequestService.findJoinRequestsForTeam(id, pageable);
        return ResponseEntity.ok(assembler.toModel(joinRequestPage, persistentEntityResourceAssembler));
    }

}
