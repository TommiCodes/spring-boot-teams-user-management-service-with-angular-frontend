package com.usermanagement.controller;

import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.requests.CreateUserRequest;
import com.usermanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@BasePathAwareController
public class UserController {

    private final UserService userService;

    // TODO: add Specifications
    @GetMapping("/users")
    public ResponseEntity<?> findAll(Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler, PersistentEntityResourceAssembler persistentEntityResourceAssembler) {
        Page<User> usersPage = userService.findAll(pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(usersPage, persistentEntityResourceAssembler));
    }

    // TODO: Change to created http response
    @PostMapping("/users")
    public ResponseEntity<User> create(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.create(createUserRequest);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, PersistentEntityResourceAssembler assembler) {
        User user = this.userService.get(id);
        return ResponseEntity.ok(assembler.toFullResource(user));
    }

    @GetMapping("/users/{id}/teams")
    public ResponseEntity<?> findAllTeamsForUser(@PathVariable Long id, Pageable pageable, PagedResourcesAssembler assembler, PersistentEntityResourceAssembler persistentEntityResourceAssembler) {
        Page<Team> teamList = userService.findAllTeamsForUser(id, pageable);
        return ResponseEntity.ok(assembler.toModel(teamList, persistentEntityResourceAssembler));
    }

}
