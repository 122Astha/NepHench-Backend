package com.example.NepHench.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String roleName;
    private String email;
    private String phone;
    private String confirmPassword;
    private String liscenceNo;
    private String frontImage;
    private String backImage;
    private  String confirmationImage;
    private  String serviceProviderRoleName;

    public String getUsername() {
      return  username;
    }

    public String getPassword() {
        return password;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() { return phone;}

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getLiscenceNo(){ return liscenceNo;}
    public String getFrontImage(){return frontImage;}
    public String getBackImage(){return backImage;}
    public String getConfirmationImage(){return confirmationImage;}
    public String getServiceProviderRoleName(){return serviceProviderRoleName;}

    public void setId(Integer id) {
    }

    public void setStatus(String status) {
    }

    // Constructors, getters, and setters
}
