package com.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Set;

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

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "teams", fetch = FetchType.LAZY)
    private Set<UserTeam> users;

}
