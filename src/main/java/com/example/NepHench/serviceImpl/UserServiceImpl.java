package com.example.NepHench.serviceImpl;

import com.example.NepHench.beans.ApiResponse;
import com.example.NepHench.beans.UserRegistrationRequest;
import com.example.NepHench.model.Role;
import com.example.NepHench.model.Serviceproviderrole;
import com.example.NepHench.model.User;
import com.example.NepHench.repository.RoleRepository;
import com.example.NepHench.repository.ServiceProviderRoleRepository;
import com.example.NepHench.repository.UserRepository;
import com.example.NepHench.service.EmailService;
import com.example.NepHench.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.NepHench.utils.VerificationTokenGenerator.generateVerificationToken;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ServiceProviderRoleRepository serviceProviderRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<ApiResponse> registerUser(UserRegistrationRequest user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            ApiResponse response = new ApiResponse("Username already exists");
            return ResponseEntity.badRequest().body(response);
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            ApiResponse response = new ApiResponse("Email already exists");
            return ResponseEntity.badRequest().body(response);
        }
        //Validating the password fields
        if (!Objects.equals(user.getPassword(), user.getConfirmPassword())) {
            ApiResponse response = new ApiResponse("Passwords doesn't match");
            return ResponseEntity.badRequest().body(response);
        }
        // Generate verification token
        String verificationToken = generateVerificationToken();

        //Encoding the password
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        //getting the role nad the services provided by them
        String roleName = user.getRoleName();

        //Cases to know the role name provided to the user and send the request body according to it
        switch (roleName) {
            case "Customer" -> {
                User users = new User();
                users.setUsername(user.getUsername());
                users.setPassword(encodedPassword);
                users.setConfirmPassword(encodedPassword);
                users.setEmail(user.getEmail());
                users.setPhone(user.getPhone());
                users.setVerificationToken(verificationToken);


                // Find the role by name
                Role role = roleRepository.findByName(user.getRoleName());
                if (role == null) {
                    ApiResponse response = new ApiResponse("Invalid role name");
                    return ResponseEntity.badRequest().body(response);
                }
                users.setRole(role);
                // Send verification email to user's email address
                emailService.sendVerificationEmail(user.getEmail(), verificationToken);
                userRepository.save(users);

            }
            case "Service Provider" -> {
                String sprole = user.getServiceProviderRoleName();
                // Find the role by name
                Role roles = roleRepository.findByName(user.getRoleName());
                Serviceproviderrole sproles = serviceProviderRoleRepository.findByRoleName(user.getServiceProviderRoleName());

                if (roles == null) {
                    ApiResponse response = new ApiResponse("Invalid role name");
                    return ResponseEntity.badRequest().body(response);
                }


                // Mandatory fields for serviceprovider
                String lisceneNo = user.getLiscenceNo();
                String frontImage = user.getFrontImage();
                String backImage = user.getBackImage();
                String confirmationImage = user.getConfirmationImage();
                String role = user.getServiceProviderRoleName();

                if (lisceneNo == null || frontImage == null || backImage ==null || confirmationImage == null || role == null){
                     ApiResponse response = new ApiResponse("Enter all the required fields");
                    return ResponseEntity.badRequest().body(response);
                }

                //Creating new user
                new User();
                User spuser = new User();
                spuser.setUsername(user.getUsername());
                spuser.setPassword(encodedPassword);
                spuser.setConfirmPassword(encodedPassword);
                spuser.setEmail(user.getEmail());
                spuser.setPhone(user.getPhone());
                spuser.setLicenseNumber(user.getLiscenceNo());
                spuser.setFrontImage(user.getFrontImage().getBytes());
                spuser.setBackImage(user.getBackImage().getBytes());
                spuser.setConfirmationImage(user.getConfirmationImage().getBytes());
                spuser.setServiceprovider(sproles);
                spuser.setRole(roles);
                spuser.setVerificationToken(verificationToken);
                emailService.sendVerificationEmail(spuser.getEmail(), verificationToken);
                userRepository.save(spuser);
            }
            default -> {
                ApiResponse response = new ApiResponse("Invalid role name");
                return ResponseEntity.badRequest().body(response);
            }
        }
            ApiResponse response = new ApiResponse("User registered successfully. Please check your email for verification");
            return ResponseEntity.ok(response);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User login(String Email, String password){
        // Find the user by username or email
        Optional<User> userOptional = userRepository.findByEmail(Email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
             {
                // Validate the password
                if (passwordEncoder.matches(password, user.getPassword())) {
                    if(user.isVerified()) {
                        return user;
                    }
                    throw new UserNotVerifiedException("User not verified");
                }
                 throw new InvalidCredentialsException("Invalid username/email or password.");

            }
        }
        throw new EmailNotVerifiedException("User not found");
    }

    @Override
    public Object adminLogin(String username, String password){
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String rolename = user.getRole().getName();
            {
                // Validate the password
                if (password.matches(user.getPassword()) && rolename.equals("Admin")) {
                    return user;
                }
            }
        }
        return "Invalid Credentials";
    }

    @Override
    public List<User> getAllCustomers() {
        Integer role_id = 2;
        return userRepository.findAllByRoleId(role_id);
    }
    @Override
    public List<User> getAllServiceProvider() {
        Integer role_id = 3;
        return userRepository.findAllByRoleId(role_id);
    }

    @Override
    public boolean verifyUser(String verificationToken) {
        User user = userRepository.findByVerificationToken(verificationToken);
        if (user == null || user.isVerified()) {
            return false; // User not found or already verified
        }

        // Verify the user
        user.setVerified(true);
        userRepository.save(user);
        return true;
    }

}
