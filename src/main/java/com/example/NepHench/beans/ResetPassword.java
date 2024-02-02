package com.example.NepHench.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {
    private String token;
    private String newPassword;
}
