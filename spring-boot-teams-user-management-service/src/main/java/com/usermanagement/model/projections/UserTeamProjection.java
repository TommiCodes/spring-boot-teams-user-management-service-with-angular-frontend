package com.usermanagement.model.projections;

import com.usermanagement.model.Role;
import com.usermanagement.model.User;
import com.usermanagement.model.UserTeamRelation;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "users", types = { UserTeamRelation.class })
public interface UserTeamProjection {

    User getUser();
    Role getRole();

}
