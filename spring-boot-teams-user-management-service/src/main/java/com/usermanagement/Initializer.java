package com.usermanagement;

import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.requests.CreateTeamRequest;
import com.usermanagement.requests.CreateUserRequest;
import com.usermanagement.service.TeamService;
import com.usermanagement.service.UserService;
import com.usermanagement.service.UserTeamService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Initializer implements CommandLineRunner {

    private final UserService userService;
    private final TeamService teamService;
    private final UserTeamService userTeamService;

    @Override
    public void run(String... args) throws Exception {

        // Init User

        CreateUserRequest createArnold = CreateUserRequest.builder()
                .username("Test")
                .firstname("Arnold")
                .lastname("Schwarzenegger")
                .password("PasswordToHash")
                .email("arnold@schwarzenegger.com")
                .build();

        CreateUserRequest createDorian = CreateUserRequest.builder()
                .username("Tester")
                .firstname("Dorian")
                .lastname("Yates")
                .password("PasswordToHash")
                .email("dorian@yates.com")
                .build();

        User arnold = userService.create(createArnold);
        User dorian = userService.create(createDorian);

        // Init Team

        CreateTeamRequest createManu = CreateTeamRequest.builder()
                .name("Manu FC")
                .build();

        Team manu = teamService.create(createManu);

        CreateTeamRequest createChelsea = CreateTeamRequest.builder()
                .name("Chelsea")
                .build();

        Team chelsea = teamService.create(createChelsea);

        // Add Users to Teams
        userTeamService.addUserToTeam(manu.getId(), arnold.getId());
        userTeamService.addUserToTeam(manu.getId(), dorian.getId());
        userTeamService.addUserToTeam(chelsea.getId(), dorian.getId());

        // Remove user from team
        userTeamService.removeUserFromTeam(manu.getId(), arnold.getId());

        // show user
        userTeamService.getTeamsForUser(dorian.getId());

    }
}