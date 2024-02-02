package com.example.NepHench.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @NotNull
    @Column(name = "used", nullable = false)
    private Boolean used = false;

    @Column(name = "created_at")
    private Instant createdAt;

    public boolean isUsed() {
        return used;
    }

}