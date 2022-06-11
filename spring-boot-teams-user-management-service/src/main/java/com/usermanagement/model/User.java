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

@EqualsAndHashCode(callSuper = true, exclude = { "teams", "joinRequest" } )
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "USERS")
@EntityListeners(AuditingEntityListener.class)
public class User extends RepresentationModel<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private boolean enabled;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    // Spring Data, customize Url Path and Relation Name
    @RestResource(rel = "joinRequests", path = "join-requests")
    @Getter(onMethod_=@JsonIgnore)
    private List<JoinRequest> joinRequests;

    // use JsonIgnore, to eliminate circular Json Mapping
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<UserTeamRelation> teams;


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
