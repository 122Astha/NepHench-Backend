package com.example.NepHench.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceProviderLoginResponse {
    private Integer id;
    private String username;
    private String roleName;
    private String email;
    private String liscenceNo;
    private String frontImage;
    private String backImage;
    private  String confirmationImage;
    private  String serviceProviderRoleName;
    private String status;

    public String getUsername() {
        return  username;
    }



    public String getRoleName() {
        return roleName;
    }

    public String getEmail() {
        return email;
    }


    public String getLiscenceNo(){ return liscenceNo;}
    public String getFrontImage(){return frontImage;}
    public String getBackImage(){return backImage;}
    public String getConfirmationImage(){return confirmationImage;}
    public String getServiceProviderRoleName(){return serviceProviderRoleName;}

    public void setStatus(String status) {
    }

    // Constructors, getters, and setters
}
