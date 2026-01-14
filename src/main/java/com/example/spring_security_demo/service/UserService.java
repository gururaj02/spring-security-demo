package com.example.spring_security_demo.service;

import com.example.spring_security_demo.entity.RegisterUserRequest;
import com.example.spring_security_demo.entity.UserResponse;
import com.example.spring_security_demo.entity.Users;
import com.example.spring_security_demo.repo.UserDetailsRepository;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(RegisterUserRequest registerUserRequest) {
        // TODO - check is user already exists
        if(userDetailsRepository.findByUsername(registerUserRequest.getUsername()).isPresent()) {
            throw new RuntimeException("User Already Exist!");
        }

        // TODO - encode password in request
        Users users = new Users();
        users.setUsername(registerUserRequest.getUsername());
        users.setRole(registerUserRequest.getRole());
        users.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));

        // TODO - save user
        Users savedUser = userDetailsRepository.save(users);

        return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getRole().name());
    }
}
