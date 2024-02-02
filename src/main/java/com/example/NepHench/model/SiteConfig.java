package com.example.NepHench.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "site_config")
public class SiteConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "site_key", nullable = false)
    private String siteKey;

    @Size(max = 255)
    @NotNull
    @Column(name = "site_value", nullable = false)
    private String siteValue;

    @Column(name = "image")
    private byte[] image;

}