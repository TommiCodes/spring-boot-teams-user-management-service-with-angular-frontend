package com.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usermanagement.model.enums.Privileges;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "PRIVILEGES")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Privileges privilege;

    @ManyToMany(mappedBy = "privileges")
    @JsonIgnore
    private Collection<Role> roles;
}