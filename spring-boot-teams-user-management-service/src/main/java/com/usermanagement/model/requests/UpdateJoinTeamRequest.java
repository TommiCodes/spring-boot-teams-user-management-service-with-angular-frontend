package com.usermanagement.model.requests;

import com.usermanagement.model.enums.JoinStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJoinTeamRequest {

    private JoinStatus joinStatus;

}
