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
        User arnold = createUser("ArnoldTester", "Arnold", "Schwarzenegger", "PasswordToHash", "arnold@schwarzenegger.com");
        User dorian = createUser("DorianTester", "Dorian", "Yates", "PasswordToHash123", "dorian@yates.com");

        // Create Roles and Privileges
        Privilege teamMemberPrivilege = createPrivilege(Privileges.MEMBER);
        Privilege teamAdminPrivilege = createPrivilege(Privileges.ADMIN);

        List<Privilege> adminPrivileges = Arrays.asList(teamMemberPrivilege, teamAdminPrivilege);

        // Admin has the Admin and the Member Privilege
        Role adminRole = createRole(Roles.ADMIN, adminPrivileges);

        // Member only has the MemberPrivilege
        Role memberRole = createRole(Roles.MEMBER, List.of(teamMemberPrivilege));

        // Init Team
        CreateTeamRequest createManu = CreateTeamRequest.builder()
                .name("Manu FC")
                .build();

        teamService.create(createManu, arnold);

        CreateTeamRequest createChelsea = CreateTeamRequest.builder()
                .name("Chelsea")
                .build();

        teamService.create(createChelsea, dorian);

        // Create more teams
        teamService.create(CreateTeamRequest.builder().name("Bayern").build(), dorian);
        teamService.create(CreateTeamRequest.builder().name("Chelsea").build(), dorian);
        teamService.create(CreateTeamRequest.builder().name("Real").build(), dorian);
        teamService.create(CreateTeamRequest.builder().name("PSG").build(), dorian);
        teamService.create(CreateTeamRequest.builder().name("Marseille").build(), dorian);
        teamService.create(CreateTeamRequest.builder().name("Dortmund").build(), dorian);
        teamService.create(CreateTeamRequest.builder().name("Tottenham").build(), dorian);
        teamService.create(CreateTeamRequest.builder().name("Mailand").build(), dorian);
        teamService.create(CreateTeamRequest.builder().name("Rom").build(), dorian);
        teamService.create(CreateTeamRequest.builder().name("City").build(), dorian);

        // Add Users to Teams
/*
        userService.addTeamToUser(manu.getId(), arnold.getId());
        userService.addTeamToUser(manu.getId(), dorian.getId());
        userService.addTeamToUser(chelsea.getId(), dorian.getId());

*/
        // Remove user from team
/*        userService.removeTeamFromUser(manu.getId(), arnold.getId());*/

    }

    private User createUser(String username, String firstname, String lastname, String password, String email) {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .password(password)
                .email(email)
                .build();
        return this.userService.create(createUserRequest);
    }

    private Privilege createPrivilege(Privileges privilege) {
        Privilege p = new Privilege();
        p.setPrivilege(privilege);
        return privilegeRepository.save(p);
    }

    private Role createRole(Roles role, List<Privilege> privilegesList) {
        Role r = new Role();
        r.setRole(role);
        r.setPrivileges(privilegesList);
        return roleRepository.save(r);
    }
}