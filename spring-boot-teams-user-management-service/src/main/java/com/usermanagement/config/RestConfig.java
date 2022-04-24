package com.usermanagement.config;

import com.usermanagement.model.JoinRequest;
import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.model.projections.JoinRequestDetailsProjection;
import com.usermanagement.model.projections.UserProjection;
import com.usermanagement.model.projections.UserTeamProjection;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfig implements RepositoryRestConfigurer{

        @Override
        public void configureRepositoryRestConfiguration(
                RepositoryRestConfiguration repositoryRestConfiguration,
                CorsRegistry cors) {

            repositoryRestConfiguration
                    // expose ids
                    .exposeIdsFor(Team.class, User.class, JoinRequest.class)
                    .getProjectionConfiguration()
                    .addProjection(UserProjection.class)
                    .addProjection(UserTeamProjection.class)
                    .addProjection(JoinRequestDetailsProjection.class);
        }
}
