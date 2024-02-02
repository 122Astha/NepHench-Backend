package com.example.NepHench.beans;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServiceRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String role;
    private String image;

}
