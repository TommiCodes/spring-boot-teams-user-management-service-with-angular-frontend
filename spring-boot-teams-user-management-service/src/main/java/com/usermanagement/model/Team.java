package com.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "TEAMS")
@RestResource()
@EntityListeners(AuditingEntityListener.class)
public class Team extends RepresentationModel<Team> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    // use JsonIgnore, to eliminate circular Json Mapping
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<UserTeamRelation> users;

    // Bidirectional OneToMany
    @JsonIgnore
    // Spring Data, customize Url Path and Relation Name
    @RestResource(rel = "joinRequests", path = "join-requests")
    @ToString.Exclude
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<JoinRequest> joinRequests;

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
