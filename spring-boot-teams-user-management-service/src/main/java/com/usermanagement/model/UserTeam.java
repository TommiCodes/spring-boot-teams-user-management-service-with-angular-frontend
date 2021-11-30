package com.usermanagement.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_team")
public class UserTeam {

    @EmbeddedId
    private UserTeamKey id;

    // Use EAGER, so the User gets Loaded "into" the UserTeam
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    // Use EAGER, so the Team gets Loaded "into" the UserTeam
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private Team team;

}
