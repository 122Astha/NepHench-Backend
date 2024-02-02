package com.example.NepHench.beans;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@JsonSerialize
@Getter
@Setter
public class ApiResponse {
    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }

}
