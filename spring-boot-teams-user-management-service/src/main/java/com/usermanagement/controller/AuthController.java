package com.usermanagement.controller;

import com.usermanagement.model.User;
import com.usermanagement.model.requests.JwtAuthenticationResponse;
import com.usermanagement.model.requests.LoginRequest;
import com.usermanagement.service.AuthenticationService;
import com.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

@BasePathAwareController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/auth/login")
    @Transactional
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.login(user, loginRequest.getPassword());
        return ResponseEntity.ok(jwtAuthenticationResponse);
    }

}
