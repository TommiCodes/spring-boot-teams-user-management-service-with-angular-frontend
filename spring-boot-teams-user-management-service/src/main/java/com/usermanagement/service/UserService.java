package com.usermanagement.service;

import com.usermanagement.model.*;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.requests.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class UserService {

    private final TeamService teamService;
    private final UserTeamService userTeamService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void addTeamToUser(Long teamId, Long userId) {
        Team team = teamService.get(teamId);
        User user = get(userId);
        // Role id = 2 is always the MEMBER Role
        Role role = roleRepository.getById(2L);

        UserTeam userTeam = userTeamService.build(user, team, role);

        // check if user already in team
        if (get(teamId).getTeams().contains(userTeam)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already in team");
        }

        userTeamService.save(userTeam);
    }

    public void removeTeamFromUser(Long teamId, Long userId) {
        Team team = teamService.get(teamId);
        User user = get(userId);

        UserTeamKey userTeamKey = UserTeamKey.builder()
                .teamId(team.getId())
                .userId(user.getId())
                .build();

        UserTeam userTeam = userTeamService.getById(userTeamKey);

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

        // Create User
        User user = User.builder()
                .email(createUserRequest.getEmail())
                .firstname(createUserRequest.getFirstname())
                .lastname(createUserRequest.getLastname())
                .password(createUserRequest.getPassword())
                .username(createUserRequest.getUsername())
                .build();

        return userRepository.save(user);
    }

    public Page<Team> findAllTeamsForUser(Long userId, Pageable pageable) {
        User user = get(userId);

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
