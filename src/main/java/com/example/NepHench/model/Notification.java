package com.example.NepHench.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    @Type(type = "org.hibernate.type.TextType")
    private String content;

    @Column(name = "\"timestamp\"")
    private Instant timestamp;

    @Column(name = "is_read")
    private Boolean isRead;
}