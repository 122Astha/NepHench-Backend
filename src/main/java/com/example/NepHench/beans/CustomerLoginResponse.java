package com.example.NepHench.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerLoginResponse {
    private Integer id;
    private String username;
    private String roleName;
    private String email;
    private String status;

    // Constructors, getters, and setters
}
