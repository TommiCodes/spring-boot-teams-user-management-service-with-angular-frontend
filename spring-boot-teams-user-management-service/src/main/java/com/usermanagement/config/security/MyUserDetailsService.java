package com.usermanagement.config.security;

import com.usermanagement.model.Privilege;
import com.usermanagement.model.User;
import com.usermanagement.model.UserTeam;
import com.usermanagement.service.UserService;
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
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userService.get(Long.parseLong(userId, 10));

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User: " + userId + " not found");
        }

        // TODO: Ggf. mit logik erweitern
        return org.springframework.security.core.userdetails.User.withUsername(userId).password(user.getPassword())
                .authorities(getAuthorities(user.getTeams())).accountExpired(false).accountLocked(false)
                .credentialsExpired(false).disabled(false).build();
    }

    private List<? extends  GrantedAuthority> getAuthorities(List<UserTeam> userTeamList) {
        return getGrantedAuthorities(getPrivileges(userTeamList));
    }

    private List<String> getPrivileges(List<UserTeam> userTeams) {
        List<Privilege> privilegeList = new ArrayList<>();
        List<String> privilegeStringList = new ArrayList<>();
        for (UserTeam userTeam : userTeams) {
            privilegeList.addAll(userTeam.getRole().getPrivileges());
        }
        for(Privilege item : privilegeList) {
            privilegeStringList.add(item.getPrivilege().name());
        }
        return privilegeStringList;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
