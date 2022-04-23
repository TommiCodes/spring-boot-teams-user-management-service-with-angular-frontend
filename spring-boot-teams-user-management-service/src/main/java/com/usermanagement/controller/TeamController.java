package com.usermanagement.controller;

import com.usermanagement.model.JoinRequest;
import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.requests.CreateTeamRequest;
import com.usermanagement.service.JoinRequestService;
import com.usermanagement.service.TeamService;
import com.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@BasePathAwareController
public class TeamController {

    private final TeamService teamService;
    private final JoinRequestService joinRequestService;
    private final UserService userService;

    @GetMapping("/teams")
    public ResponseEntity<?> findAllTeams(Pageable pageable, @RequestParam(required = false) String name, PagedResourcesAssembler pagedResourcesAssembler, PersistentEntityResourceAssembler persistentEntityResourceAssembler) {
        Page<Team> teamPage = teamService.search(pageable, name);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(teamPage, persistentEntityResourceAssembler));
    }

    @PostMapping("/teams")
    public ResponseEntity<?> createTeam(@RequestBody CreateTeamRequest createTeamRequest) {
        // Get the "subject" from the security context holder (it's the id as string in our case)
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        // get the user by id (the user who wants to create the team), so he will get admin rights for the team
        User admin = userService.findById(Long.parseLong(userId, 10));
        teamService.create(createTeamRequest, admin);
        return ResponseEntity.ok("Created");
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id, PersistentEntityResourceAssembler assembler) {
        Team team = teamService.findById(id);
        return ResponseEntity.ok(assembler.toModel(team));
    }

    @PostMapping("/teams/{id}/leave")
    public ResponseEntity<?> leaveTeam(@PathVariable Long id) {
        // Get the "subject" from the security context holder (it's the id as string in our case)
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        // get the user by id (the user who wants to create the team), so he will get admin rights for the team
        User user = userService.findById(Long.parseLong(userId, 10));
        Team team = teamService.findById(id);

        userService.removeTeamFromUser(team.getId(), user.getId());
        return ResponseEntity.ok("User left team");
    }

    @GetMapping("/teams/{id}/users")
    public ResponseEntity<?> findAllUsersForTeam(@PathVariable Long id, Pageable pageable, PagedResourcesAssembler assembler) {
        Page<User> userPage = teamService.findAllUsersForTeam(id, pageable);
        return ResponseEntity.ok(assembler.toModel(userPage));
    }

    @PostMapping("/teams/{id}/send-join-request")
    public ResponseEntity<?> sendJoinRequestForTeam(@PathVariable Long id) {
        // The user who is sending the join request is always coming from the jwt/the Sprin Userdetails
        UserDetails auth = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        joinRequestService.save(id, Long.parseLong(auth.getUsername()));
        return ResponseEntity.ok("Join Request created");
    }

    // TODO: add Specifications to search for the JoinRequest Status
    // PreAuthorize so that only Team Admins can see the join requests for their team
    @PreAuthorize("isTeamAdmin(#id)")
    // If you want to inline the user & team to the joinRequest, add "?projection=details" to your request
    @GetMapping("/teams/{id}/join-requests")
    public ResponseEntity<?> findJoinRequestsForTeam(@PathVariable("id") Long id, Pageable pageable, PagedResourcesAssembler assembler, PersistentEntityResourceAssembler persistentEntityResourceAssembler) {
        Page<JoinRequest> joinRequestPage = joinRequestService.findJoinRequestsForTeam(id, pageable);
        return ResponseEntity.ok(assembler.toModel(joinRequestPage, persistentEntityResourceAssembler));
    }

}
