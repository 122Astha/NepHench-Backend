package com.example.NepHench.beans;

public class ReviewRequest {
    private Integer customer;
    private Integer serviceProvider;
    private String reviewText;
    private Integer rating;

    public  Integer getCustomer(){
        return customer;
    }

    public  Integer getServiceProvider(){
        return serviceProvider;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Integer getRating() {
        return rating;
    }
}
