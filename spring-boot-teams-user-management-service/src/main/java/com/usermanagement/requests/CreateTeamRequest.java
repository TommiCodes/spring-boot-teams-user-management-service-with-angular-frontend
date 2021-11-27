package com.usermanagement.requests;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateTeamRequest {
    String name;
}
