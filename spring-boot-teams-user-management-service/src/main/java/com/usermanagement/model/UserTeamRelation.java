package com.usermanagement.model;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "USER_TEAM_RELATION")
@EntityListeners(AuditingEntityListener.class)
public class UserTeamRelation {

    @EmbeddedId
    private UserTeamRelationKey id;

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

    @OneToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Role role;

    /////////////////////////
    // Auditing Properties //
    /////////////////////////

    // gets the String from our AuditorAware (config)
    @CreatedBy
    private String createdBy;
    // gets the String from our AuditorAware (config)
    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column
    private LocalDateTime lastModifiedDateTime;

}
