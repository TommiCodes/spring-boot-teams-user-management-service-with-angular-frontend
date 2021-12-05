package com.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "teams")
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
    private List<UserTeam> users;

    // Bidirectional OneToMany
    @JsonIgnore
    // Spring Data, customize Url Path and Relation Name
    @RestResource(rel = "joinRequests", path = "join-requests")
    @ToString.Exclude
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<JoinRequest> joinRequests;

}
