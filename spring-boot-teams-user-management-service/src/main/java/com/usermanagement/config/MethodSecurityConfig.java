package com.usermanagement.config;

import com.usermanagement.config.annotations.CustomSecurityExpressionHandler;
import com.usermanagement.service.TeamService;
import com.usermanagement.service.UserTeamService;
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
    private final UserTeamService userTeamService;


    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new CustomSecurityExpressionHandler(teamService, userTeamService);
    }
}
