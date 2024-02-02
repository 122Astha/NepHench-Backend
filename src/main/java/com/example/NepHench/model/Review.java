package com.example.NepHench.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_provider_id")
    private User serviceProvider;

    @Column(name = "review_text")
    @Type(type = "org.hibernate.type.TextType")
    private String reviewText;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created_at")
    private Instant createdAt;

}