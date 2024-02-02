package com.example.NepHench.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "serviceproviderroles")
public class Serviceproviderrole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "role_name", length = 50)
    private String roleName;

    @Column(name = "description")
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @Size(max = 1000)
    @Column(name = "image", length = 1000)
    private String image;

}