package com.example.NepHench.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmountRequest {
    private Integer customerId;
    private Integer serviceProviderId;
    private Integer serviceId;
    private Double totalAmount;
}
