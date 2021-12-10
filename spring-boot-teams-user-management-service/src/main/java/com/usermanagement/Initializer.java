package com.usermanagement;

import com.usermanagement.model.Privilege;
import com.usermanagement.model.Role;
import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.model.enums.Privileges;
import com.usermanagement.model.enums.Roles;
import com.usermanagement.repository.PrivilegeRepository;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.requests.CreateTeamRequest;
import com.usermanagement.requests.CreateUserRequest;
import com.usermanagement.service.TeamService;
import com.usermanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Component
public class Initializer implements CommandLineRunner {

    private final UserService userService;
    private final TeamService teamService;

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;

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

        // Create Roles and Privileges
        Privilege teamMemberPrivilege = new Privilege();
        teamMemberPrivilege.setPrivilege(Privileges.TEAM_MEMBER);
        privilegeRepository.save(teamMemberPrivilege);

        Privilege teamAdminPrivilege = new Privilege();
        teamAdminPrivilege.setPrivilege(Privileges.TEAM_ADMIN);
        privilegeRepository.save(teamAdminPrivilege);

        List<Privilege> adminPrivileges = Arrays.asList(
                teamMemberPrivilege,
                teamAdminPrivilege
        );

        // Admin has the Admin and the Member Privilege
        Role adminRole = new Role();
        adminRole.setRole(Roles.ADMIN);
        adminRole.setPrivileges(adminPrivileges);
        roleRepository.save(adminRole);

        // Member only has the MemberPrivilege
        Role memberRole = new Role();
        memberRole.setRole(Roles.MEMBER);
        memberRole.setPrivileges(List.of(teamMemberPrivilege));
        roleRepository.save(memberRole);

        // Add Users to Teams
        userService.addTeamToUser(manu.getId(), arnold.getId());
        userService.addTeamToUser(manu.getId(), dorian.getId());
        userService.addTeamToUser(chelsea.getId(), dorian.getId());

        // Remove user from team
        userService.removeTeamFromUser(manu.getId(), arnold.getId());

    }
}