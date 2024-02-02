package com.example.NepHench.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "login")
public class Login {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "username", length = 50)
    private String username;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

}