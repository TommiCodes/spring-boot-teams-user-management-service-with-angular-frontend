package com.usermanagement.controller;

import com.usermanagement.model.JoinRequest;
import com.usermanagement.model.enums.JoinStatus;
import com.usermanagement.model.requests.UpdateJoinTeamRequest;
import com.usermanagement.service.JoinRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BasePathAwareController
@RequiredArgsConstructor
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    // Add Custom Security Expression where is checked if the user is Admin of the team that is handling the request
    @PreAuthorize("isTeamAdminOfJoinRequest(#id)")
    @PutMapping("/join-requests/{id}")
    public ResponseEntity<?> handleJoinRequest(@PathVariable("id") Long id, @RequestBody UpdateJoinTeamRequest updateJoinTeamRequest) {
        JoinRequest joinRequest = joinRequestService.handle(id, updateJoinTeamRequest);
        return joinRequest.getJoinStatus() == JoinStatus.ACCEPTED ? ResponseEntity.ok("User Join accepted") : ResponseEntity.ok("User Join declined");
    }

}
