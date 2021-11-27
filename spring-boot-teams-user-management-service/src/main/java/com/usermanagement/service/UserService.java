package com.usermanagement.service;

import com.usermanagement.model.User;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.requests.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public User create(CreateUserRequest createUserRequest) {

        // Check Email
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new RuntimeException("Email is already in use.");
        }

        // Check Username
        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new RuntimeException("Username is already in use.");
        }

        // Create User
        User user = User.builder()
                .email(createUserRequest.getEmail())
                .firstname(createUserRequest.getFirstname())
                .lastname(createUserRequest.getLastname())
                .password(createUserRequest.getPassword())
                .username(createUserRequest.getUsername())
                .build();

        return userRepository.save(user);
    }

}
