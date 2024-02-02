package com.example.NepHench.controller;

import com.example.NepHench.beans.*;
import com.example.NepHench.model.User;
import com.example.NepHench.repository.RoleRepository;
import com.example.NepHench.repository.UserRepository;
import com.example.NepHench.service.UserService;
import com.example.NepHench.serviceImpl.EmailNotVerifiedException;
import com.example.NepHench.serviceImpl.InvalidCredentialsException;
import com.example.NepHench.serviceImpl.UserNotVerifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //GET ALL USER
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //GET USER BY ID
    @GetMapping("/{user_id}")
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable("user_id") Integer userId) {
        Optional<User> userOptional = Optional.ofNullable(userService.getUserById(userId));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //REGISTER NEW USERS
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) throws Exception {
        ResponseEntity<ApiResponse> registeredUser = userService.registerUser(request);
        return  registeredUser;
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer user_id) {
        userRepository.deleteById(user_id);
        return ResponseEntity.ok().body("User deleted successfully");
    }

    @GetMapping("/customers")
    public List<User> getAllCustomers() {
        return userService.getAllCustomers();
    }

    @GetMapping("/serviceproviders")
    public List<User> getAllServiceProvider() {
        return userService.getAllServiceProvider();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Call the login method in the UserService
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

            String rolename = user.getRole().getName();

            switch (rolename) {
                case "Customer" -> {
                    CustomerLoginResponse response = new CustomerLoginResponse();
                    response.setId(user.getId());
                    response.setEmail(user.getEmail());
                    response.setUsername(user.getUsername());
                    response.setStatus("active");
                    response.setRoleName(rolename);
                    return ResponseEntity.ok(response);
                }
                case   "Service Provider" -> {
                    String sprole = user.getServiceprovider().getRoleName();
                    ServiceProviderLoginResponse response = new ServiceProviderLoginResponse();
                    response.setId(user.getId());
                    response.setEmail(user.getEmail());
                    response.setUsername(user.getUsername());
                    response.setStatus("active");
                    response.setRoleName(rolename);
                    response.setServiceProviderRoleName(sprole);
                    response.setLiscenceNo(user.getLicenseNumber());
                    response.setFrontImage(Arrays.toString(user.getFrontImage()));
                    response.setBackImage(Arrays.toString(user.getBackImage()));
                    response.setConfirmationImage(Arrays.toString(user.getConfirmationImage()));
                    return ResponseEntity.ok(response);
                }

            }
            return ResponseEntity.ok().body("login successfull");
        }
        catch (InvalidCredentialsException e) {

            String errorMessage = "Invalid Credentials";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", errorMessage));
        }
        catch (EmailNotVerifiedException e){
            String errorMessage = "User with this email is not found";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", errorMessage));
        }
        catch (UserNotVerifiedException e){
            String errorMessage = "User not verified";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", errorMessage));
        }
    }

    @PostMapping("/login/admin")
    public User adminLogin(@RequestBody AdminLoginRequest request){
        User user = (User) userService.adminLogin(request.getUsername(), request.getPassword());
        return user;
    }

    // Email verification process in UserController
    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String verificationToken) {
        // Verify the user based on the verification token
        boolean verified = userService.verifyUser(verificationToken);
        if (verified) {
            return ResponseEntity.ok().body("User verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification token or user already verified");
        }
    }


    @PostMapping("/update-device-token")
    public ResponseEntity<ApiResponse> updateDeviceToken(@RequestBody DeviceTokenUpdateRequest request) {
        Integer userId = request.getUserId();
        String deviceToken = request.getDeviceToken();

        // Retrieve the user from the database based on the userId
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Update the user with the new device token
            user.setDeviceToken(deviceToken);
            userRepository.save(user);

            ApiResponse response = new ApiResponse("User device token updated successfully");
            return ResponseEntity.ok(response);
        }
        ApiResponse response = new ApiResponse("User not found");
        return ResponseEntity.badRequest().body(response);
    }
}


