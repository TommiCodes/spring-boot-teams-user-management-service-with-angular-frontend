package com.usermanagement.config.annotations;

import com.usermanagement.model.*;
import com.usermanagement.model.enums.Privileges;
import com.usermanagement.repository.JoinRequestRepository;
import com.usermanagement.service.TeamService;
import com.usermanagement.service.UserTeamRelationService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final TeamService teamService;
    private final UserTeamRelationService userTeamRelationService;
    private final JoinRequestRepository joinRequestRepository;

    private Object filterObject;
    private Object returnObject;
    private Object target;

    public CustomMethodSecurityExpressionRoot(Authentication authentication, TeamService teamService, UserTeamRelationService userTeamRelationService, JoinRequestRepository joinRequestRepository) {
        super(authentication);
        this.teamService = teamService;
        this.userTeamRelationService = userTeamRelationService;
        this.joinRequestRepository = joinRequestRepository;
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

    /* Checks if the user is the admin of the team, that is linked in the JoinRequest*/
    public boolean isTeamAdminOfJoinRequest(Long joinRequestId) {
        JoinRequest joinRequest = joinRequestRepository.findById(joinRequestId).orElseThrow(()-> new RuntimeException("not found"));
        UserDetails authUser = (UserDetails) this.getPrincipal();

        UserTeamRelationKey userTeamRelationKey = UserTeamRelationKey.builder()
                .teamId(joinRequest.getTeam().getId())
                .userId(Long.parseLong(authUser.getUsername()))
                .build();

        // if no userTeam is found, then an exception gets thrown
        UserTeamRelation userTeamRelation = userTeamRelationService.findById(userTeamRelationKey);

        // if the user is not admin of the team of the joinRequest, then deny access
        // if user is in the team and is admin, then return true
        List<Privileges> teamPrivilegesList = this.getPrivileges(joinRequest.getTeam().getId());

        return teamPrivilegesList.contains(Privileges.ADMIN);
    }

    private List<Privileges> getPrivileges(Long teamId) {
        UserDetails authUser = (UserDetails) this.getPrincipal();
        Team team = this.teamService.findById(teamId);

        UserTeamRelationKey userTeamRelationKey = UserTeamRelationKey.builder()
                .teamId(team.getId())
                .userId(Long.parseLong(authUser.getUsername()))
                .build();

        UserTeamRelation userTeamRelation = userTeamRelationService.findById(userTeamRelationKey);
        return userTeamRelation.getRole().getPrivileges().stream().map(Privilege::getPrivilege).toList();
    }

}