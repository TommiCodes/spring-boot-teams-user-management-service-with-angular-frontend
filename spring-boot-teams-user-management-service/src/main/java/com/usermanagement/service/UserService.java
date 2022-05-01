package com.usermanagement.service;

import com.usermanagement.model.*;
import com.usermanagement.model.specs.UserSpecs;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.requests.CreateUserRequest;
import com.usermanagement.requests.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    // Services
    private final TeamService teamService;
    private final UserTeamService userTeamService;
    private final AuthenticationService authenticationService;
    // Repositories
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // find User by user.email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }

    // find User by Id
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }

    public User update(Long id, UpdateUserRequest updateUserRequest) {
        User user = findById(id);

        user.setUsername(updateUserRequest.getUsername());
        user.setEmail(updateUserRequest.getEmail());
        user.setFirstname(updateUserRequest.getFirstname());
        user.setLastname(updateUserRequest.getLastname());
        //  TODO: Also implement Passwort update

        return userRepository.save(user);
    }

    // find all users - paged
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> searchAll(Pageable pageable, String username) {
        return userRepository.findAll(username != null ? UserSpecs.usernameLike(username) : null, pageable);
    }

    public void addTeamToUser(Long teamId, Long userId) {
        Team team = teamService.findById(teamId);
        User user = findById(userId);
        // Role id = 2 is always the MEMBER Role
        Role role = roleRepository.findById(2L).orElseThrow(() -> new RuntimeException("Not found"));

        UserTeam userTeam = userTeamService.build(user, team, role);

        // check if user already in team
        if (findById(teamId).getTeams().contains(userTeam)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already in team");
        }

        userTeamService.save(userTeam);
    }

    public void removeTeamFromUser(Long teamId, Long userId) {
        Team team = teamService.findById(teamId);
        User user = findById(userId);

        // TODO: Check if user is last ADMIN and if yes, then throw()

        UserTeamKey userTeamKey = UserTeamKey.builder()
                .teamId(team.getId())
                .userId(user.getId())
                .build();

        UserTeam userTeam = userTeamService.findById(userTeamKey);

        userTeamService.delete(userTeam);
    }

    public User create(CreateUserRequest createUserRequest) {

        // Check Email
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new RuntimeException("Email is already in use.");
        }

        // Check Username
        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new RuntimeException("Username is already in use.");
        }

        String hashedPassword = authenticationService.hashPassword(createUserRequest.getPassword());

        // Create User
        User user = User.builder()
                .email(createUserRequest.getEmail())
                .firstname(createUserRequest.getFirstname())
                .lastname(createUserRequest.getLastname())
                .password(hashedPassword)
                .username(createUserRequest.getUsername())
                .build();

        return userRepository.save(user);
    }

    public Page<Team> findAllTeamsForUser(Long userId, Pageable pageable) {
        User user = findById(userId);

        // find the paged UserTeams
        Page<UserTeam> userTeamPage = this.userTeamService.findAllByUserId(user.getId(), pageable);
        // get the pageable
        Pageable userTeamPageable = userTeamPage.getPageable();

        // get the teams from the UserTeamsPage
        List<Team> teamList = userTeamPage.stream().map(UserTeam::getTeam).toList();

        // make a Page<Team> and return it
        return new PageImpl<>(teamList, userTeamPageable, teamList.size());
    }

}
