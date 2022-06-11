package com.usermanagement.config;

import com.usermanagement.config.annotations.CustomSecurityExpressionHandler;
import com.usermanagement.repository.JoinRequestRepository;
import com.usermanagement.service.TeamService;
import com.usermanagement.service.UserTeamRelationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@AllArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    private final TeamService teamService;
    private final UserTeamRelationService userTeamRelationService;
    private final JoinRequestRepository joinRequestRepository;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new CustomSecurityExpressionHandler(teamService, userTeamRelationService, joinRequestRepository);
    }
}
