package com.example.NepHench.beans;

import java.util.List;

public class InvoiceRequest {

    private Integer customerId;
    private List<Integer> serviceId;

    public InvoiceRequest() {}

    public InvoiceRequest(Integer customerId, List<Integer> serviceId) {
        this.customerId = customerId;
        this.serviceId = serviceId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<Integer> getServiceId() {
        return serviceId;
    }
    public void setServiceId(List<Integer> serviceId) {
        this.serviceId = serviceId;
    }
}

