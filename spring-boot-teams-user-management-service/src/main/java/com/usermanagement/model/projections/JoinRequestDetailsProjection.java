package com.usermanagement.model.projections;

import com.usermanagement.model.JoinRequest;
import com.usermanagement.model.Team;
import com.usermanagement.model.User;
import com.usermanagement.model.enums.JoinStatus;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "details", types = { JoinRequest.class })
public interface JoinRequestDetailsProjection {

    Long getId();

    JoinStatus getJoinStatus();

    User getUser();

    Team getTeam();

}
