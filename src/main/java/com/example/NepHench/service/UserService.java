package com.example.NepHench.service;

import com.example.NepHench.beans.ApiResponse;
import com.example.NepHench.beans.UserRegistrationRequest;
import com.example.NepHench.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<ApiResponse> registerUser(UserRegistrationRequest user) throws Exception;

    List<User> getAllUsers();
//    User getUserById(Long user_id);

    List<User> getAllCustomers();

    User getUserById(Integer userId);

    User login(String usernameOrEmail, String password);

    List<User> getAllServiceProvider();

    boolean verifyUser(String verificationToken);

    Object adminLogin(String username, String password);
}
