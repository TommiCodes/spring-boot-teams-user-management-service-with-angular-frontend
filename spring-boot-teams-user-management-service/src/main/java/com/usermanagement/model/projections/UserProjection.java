package com.usermanagement.model.projections;

import com.usermanagement.model.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "userProfile", types = { User.class })
public interface UserProjection {

    Long getId();
    String getUsername();
    String getEmail();
    String getFirstname();
    String getLastname();

}
