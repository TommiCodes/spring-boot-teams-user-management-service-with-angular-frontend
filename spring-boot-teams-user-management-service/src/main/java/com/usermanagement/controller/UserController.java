package com.usermanagement.controller;

import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.model.requests.CreateUserRequest;
import com.usermanagement.model.requests.UpdateUserRequest;
import com.usermanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@BasePathAwareController
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> findAll(Pageable pageable, @RequestParam(required = false) String username, PagedResourcesAssembler pagedResourcesAssembler, PersistentEntityResourceAssembler persistentEntityResourceAssembler) {
        Page<User> usersPage = userService.searchAll(pageable, username);
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
        User user = this.userService.findById(id);
        return ResponseEntity.ok(assembler.toFullResource(user));
    }

    @PreAuthorize("isOwnProfile(#id)")
    @PutMapping("/users/{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody UpdateUserRequest updateUserRequest, PersistentEntityResourceAssembler assembler) {
        User user = this.userService.update(id, updateUserRequest);
        return ResponseEntity.ok(assembler.toFullResource(user));
    }

    @GetMapping("/users/{id}/teams")
    public ResponseEntity<?> findAllTeamsForUser(@PathVariable Long id, Pageable pageable, PagedResourcesAssembler assembler, PersistentEntityResourceAssembler persistentEntityResourceAssembler) {
        Page<Team> teamList = userService.findAllTeamsForUser(id, pageable);
        return ResponseEntity.ok(assembler.toModel(teamList, persistentEntityResourceAssembler));
    }

}
