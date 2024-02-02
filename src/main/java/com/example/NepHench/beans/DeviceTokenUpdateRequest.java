package com.example.NepHench.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceTokenUpdateRequest {
    private Integer userId;
    private String deviceToken;
}
