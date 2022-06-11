package com.usermanagement.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Data
@Embeddable
public class UserTeamRelationKey implements Serializable {

    @Column(name = "users_id")
    private Long userId;

    @Column(name = "teams_id")
    private Long teamId;

}