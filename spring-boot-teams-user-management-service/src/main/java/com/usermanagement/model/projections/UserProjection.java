package com.usermanagement.model.projections;

import com.usermanagement.model.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "profile", types = { User.class })
public interface UserProjection {

    String getUsername();

}
