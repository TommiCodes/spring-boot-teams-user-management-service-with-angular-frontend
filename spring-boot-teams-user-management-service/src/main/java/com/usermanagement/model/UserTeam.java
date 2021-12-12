package com.usermanagement.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_team")
@EntityListeners(AuditingEntityListener.class)
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

    @OneToOne
/*    @ToString.Exclude*/
    private Role role;

    /////////////////////////
    // Auditing Properties //
    /////////////////////////
    // TODO: Implement with auditor aware
/*    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;*/

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column
    private LocalDateTime lastModifiedDateTime;

}
