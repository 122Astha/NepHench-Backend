package com.example.NepHench.beans;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
public class BookingRequest {

    private Integer customer;
    private Integer serviceProvider;
    private LocalDate date;
    private String time;
    private String status;

    private List<Integer> service;

    public Integer getCustomer(){
        return customer;
    }

    public Integer getServiceProvider(){
        return serviceProvider;
    }


    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("hh:mm a", Locale.US));
    }

    public String getStatus() {
        return status;
    }

    public List<Integer> getSelectedServices() {
        if (service == null) {
            return Collections.emptyList(); // Return an empty list if no services are selected
        }
        return service;
    }
}
