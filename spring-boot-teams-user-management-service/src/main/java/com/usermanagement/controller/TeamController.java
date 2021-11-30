package com.usermanagement.controller;

import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.requests.TeamJoinRequest;
import com.usermanagement.service.TeamService;
import com.usermanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@RepositoryRestController
public class TeamController {

    private final TeamService teamService;

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

}
