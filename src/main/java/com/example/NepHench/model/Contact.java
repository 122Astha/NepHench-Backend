package com.example.NepHench.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Size(max = 50)
    @NotNull
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "subject")
    @Type(type = "org.hibernate.type.TextType")
    private String subject;

    @Column(name = "message")
    @Type(type = "org.hibernate.type.TextType")
    private String message;

    @Column(name = "created_at")
    private Instant createdAt;

}