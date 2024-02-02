package com.example.NepHench.beans;

import com.example.NepHench.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequest {
    private Integer user;
    private double latitude;
    private double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
