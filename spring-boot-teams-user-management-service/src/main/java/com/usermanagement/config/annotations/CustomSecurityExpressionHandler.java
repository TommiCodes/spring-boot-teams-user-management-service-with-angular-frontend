package com.usermanagement.config.annotations;

import com.usermanagement.repository.JoinRequestRepository;
import com.usermanagement.service.TeamService;
import com.usermanagement.service.UserTeamRelationService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class CustomSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private final TeamService teamService;
    private final UserTeamRelationService userTeamRelationService;
    private final JoinRequestRepository joinRequestRepository;

    public CustomSecurityExpressionHandler(TeamService teamService, UserTeamRelationService userTeamRelationService, JoinRequestRepository joinRequestRepository) {
        this.teamService = teamService;
        this.userTeamRelationService = userTeamRelationService;
        this.joinRequestRepository = joinRequestRepository;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication, MethodInvocation invocation) {
        CustomMethodSecurityExpressionRoot root = new CustomMethodSecurityExpressionRoot(authentication, this.teamService, this.userTeamRelationService, this.joinRequestRepository);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(this.trustResolver);
        root.setRoleHierarchy(getRoleHierarchy());
        return root;
    }
}
