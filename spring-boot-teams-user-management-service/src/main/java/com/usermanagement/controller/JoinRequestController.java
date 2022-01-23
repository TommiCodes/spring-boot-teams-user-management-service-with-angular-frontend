package com.usermanagement.controller;

import com.usermanagement.model.enums.JoinStatus;
import com.usermanagement.requests.UpdateJoinTeamRequest;
import com.usermanagement.service.JoinRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BasePathAwareController
@RequiredArgsConstructor
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    // TODO: Check if user is Admin for Team
    @PutMapping("/join-requests/{id}")
    public ResponseEntity<?> handleJoinRequest(@PathVariable Long id, @RequestBody UpdateJoinTeamRequest updateJoinTeamRequest) {
        joinRequestService.handle(id, updateJoinTeamRequest);
        return updateJoinTeamRequest.getJoinStatus() == JoinStatus.DECLINED ? ResponseEntity.ok("User Join accepted") : ResponseEntity.ok("User Join declined");
    }

}
