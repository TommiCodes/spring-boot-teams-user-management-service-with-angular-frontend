package com.usermanagement.config.security;

import com.usermanagement.model.User;
import com.usermanagement.model.UserTeamRelation;
import com.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    // Transactional - to get Lazy Collections
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userRepository
                .findById(Long.parseLong(userId, 10))
                .orElseThrow(() -> new UsernameNotFoundException("User: " + userId + " not found."));

        // TODO: Ggf. mit logik erweitern
        return org.springframework.security.core.userdetails.User
                .withUsername(userId)
                .password(user.getPassword())
                .authorities(getAuthorities(user.getTeams()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false).disabled(false)
                .build();
    }

    private List<? extends  GrantedAuthority> getAuthorities(List<UserTeamRelation> userTeamRelationList) {
        return getGrantedAuthorities(getUserTeamRole(userTeamRelationList));
    }

    private List<String> getUserTeamRole(List<UserTeamRelation> userTeamRelations) {
        List<String> userTeamRole = new ArrayList<>();
         for (UserTeamRelation userTeamRelation : userTeamRelations) {
            userTeamRole.add(String.format("TEAM:%d_ROLE:%s", userTeamRelation.getTeam().getId(), userTeamRelation.getRole().getRole().name()));
        }
        return userTeamRole;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
