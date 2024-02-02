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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "username", length = 50)
    private String username;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Size(max = 255)
    @Column(name = "confirm_password")
    private String confirmPassword;

    @Size(max = 50)
    @Column(name = "address", length = 50)
    private String address;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Size(max =20)
    @Column(name = "phone" , length = 20)
    private String phone;

    @Size(max =500)
    @Column(name = "user_descp" , length = 500)
    private String userdescp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "serviceprovider_id")
    private Serviceproviderrole serviceprovider;

    @Size(max = 50)
    @Column(name = "license_number", length = 50)
    private String licenseNumber;

    @Column(name = "front_image")
    private byte[] frontImage;

    @Column(name = "back_image")
    private byte[] backImage;

    @Column(name = "confirmation_image")
    private byte[] confirmationImage;

    @Column(name = "image")
    private byte[] image;

    @NotNull
    @Column(name = "verified", nullable = false)
    private Boolean verified = false;

    @Size(max = 255)
    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "verification_expiry")
    private Instant verificationExpiry;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "total_ratings")
    private Integer totalRatings;

    @Size(max = 500)
    @Column(name = "device_token", length = 500)
    private String deviceToken;

    public boolean isVerified() {
        return verified;
    }

}