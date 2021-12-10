package com.usermanagement.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "REFRESH_TOKEN")
public class RefreshToken {

    @Id
    @Column(name = "TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "REFRESH_COUNT")
    private Long refreshCount;

    @Column(nullable = false)
    private Instant expiryDate;

}
