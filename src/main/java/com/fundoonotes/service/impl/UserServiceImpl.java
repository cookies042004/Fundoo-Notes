package com.fundoonotes.service.impl;

import com.fundoonotes.dto.request.UserRegistrationDTO;
import com.fundoonotes.entity.User;
import com.fundoonotes.exception.UserException;
import com.fundoonotes.repository.UserRepository;
import com.fundoonotes.service.UserService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // UC4:Constructor injection managed by Lombok @RequiredArgsConstructor
    private final UserRepository userRepository;

    @Override
    public User registerUser(UserRegistrationDTO dto) {
        // UC4:Business logic to check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
        if (existingUser.isPresent()) {
            throw new UserException("Email is already registered! Please login.");
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        
        // Note: Storing plain text for UC4. In later UCs (Auth & Security), we will inject PasswordEncoder.
        user.setPassword(dto.getPassword()); 

        return userRepository.save(user);
    }
}
