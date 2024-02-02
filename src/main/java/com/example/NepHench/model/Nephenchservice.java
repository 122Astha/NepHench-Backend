package com.example.NepHench.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "nephenchservices")
public class Nephenchservice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Size(max = 200)
    @NotNull
    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @NotNull
    @Column(name = "price", nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Size(max = 50)
    @NotNull
    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Size(max = 1000)
    @Column(name = "image", length = 1000)
    private String image;

}