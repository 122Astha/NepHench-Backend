package com.example.NepHench.beans;

public class UpdateProfileRequest {

    private String username;
    private String email;
    private String liscenceNo;
    private String address;
    private String image;
    private String frontImage;
    private String backImage;
    private  String confirmationImage;
private String phone;
    private String userdescp;

    public String getUsername() {
        return  username;
    }

    public String getEmail() {
        return email;
    }

    public String getLiscenceNo(){ return liscenceNo;}
    public String getFrontImage(){return frontImage;}
    public String getBackImage(){return backImage;}
    public String getConfirmationImage(){return confirmationImage;}
    public String getImage(){return image;}

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
    public String getUserdescp() {
        return userdescp;
    }
}
