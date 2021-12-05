package com.usermanagement.controller;

import com.usermanagement.model.JoinRequest;
import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.requests.JoinTeamRequest;
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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@BasePathAwareController
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final JoinRequestService joinRequestService;

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
    public ResponseEntity<?> sendJoinRequestForTeam(@PathVariable Long id, @RequestBody JoinTeamRequest joinTeamRequest) {
        joinRequestService.save(id, joinTeamRequest.getUserId());
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
