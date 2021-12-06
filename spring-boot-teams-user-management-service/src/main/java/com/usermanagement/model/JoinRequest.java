package com.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usermanagement.model.enums.JoinStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "join_requests")
@EntityListeners(AuditingEntityListener.class)
public class JoinRequest extends RepresentationModel<JoinRequest> {

    @RestResource(exported = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private JoinStatus joinStatus;

    @RestResource(exported = false)
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @RestResource(exported = false)
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="teams_id", nullable = false)
    private Team team;


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
