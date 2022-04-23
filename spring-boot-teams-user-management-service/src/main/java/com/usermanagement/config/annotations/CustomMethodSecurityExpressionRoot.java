package com.usermanagement.config.annotations;

import com.usermanagement.model.*;
import com.usermanagement.model.enums.Privileges;
import com.usermanagement.service.TeamService;
import com.usermanagement.service.UserTeamService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final TeamService teamService;
    private final UserTeamService userTeamService;
    private Object filterObject;
    private Object returnObject;
    private Object target;

    public CustomMethodSecurityExpressionRoot(Authentication authentication, TeamService teamService, UserTeamService userTeamService) {
        super(authentication);
        this.teamService = teamService;
        this.userTeamService = userTeamService;
    }
    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    void setThis(Object target) {
        this.target = target;
    }

    @Override
    public Object getThis() {
        return target;
    }

    /* Checks if the current User is having a ADMIN Privilege for the provided team*/
    public boolean isTeamAdmin(Long teamId) {
        List<Privileges> teamPrivilegesList = this.getPrivileges(teamId);
        return teamPrivilegesList.contains(Privileges.ADMIN);
    }

    /* Checks if the current User is having a MEMBER Privilege for the provided team*/
    public boolean isTeamMember(Long teamId) {
        List<Privileges> teamPrivilegesList = this.getPrivileges(teamId);
        return teamPrivilegesList.contains(Privileges.MEMBER);
    }

    /* Checks if the provided userId equals the user id of the logged in user */
    public boolean isOwnProfile(Long userId) {
        UserDetails authUser = (UserDetails) this.getPrincipal();
        return Long.parseLong(authUser.getUsername()) == userId;
    }

    private List<Privileges> getPrivileges(Long teamId) {
        UserDetails authUser = (UserDetails) this.getPrincipal();
        Team team = this.teamService.findById(teamId);

        UserTeamKey userTeamKey = UserTeamKey.builder()
                .teamId(team.getId())
                .userId(Long.parseLong(authUser.getUsername()))
                .build();

        UserTeam userTeam = userTeamService.findById(userTeamKey);
        return userTeam.getRole().getPrivileges().stream().map(Privilege::getPrivilege).toList();
    }

}