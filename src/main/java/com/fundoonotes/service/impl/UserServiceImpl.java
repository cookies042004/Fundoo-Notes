package com.fundoonotes.service.impl;

import com.fundoonotes.dto.request.UserLoginDTO;
import com.fundoonotes.dto.request.UserRegistrationDTO;
import com.fundoonotes.entity.User;
import com.fundoonotes.exception.UserException;
import com.fundoonotes.repository.UserRepository;
import com.fundoonotes.service.UserService;
import com.fundoonotes.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    
    // UC5:Injecting Password Encoder to hash passwords and JWT to map credentials
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MessageProducer producer;
    private final RedisService redisService;

    @Override
    public User registerUser(UserRegistrationDTO dto) {
        Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
        if (existingUser.isPresent()) {
            throw new UserException("Email is already registered! Please login.");
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        
        // UC5:Encrypting password with BCrypt before storing in DB
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);

        producer.sendMessage("User Registered: " + user.getEmail());

        return savedUser;
    }

    @Override
    public String loginUser(UserLoginDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserException("User not found with this email"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UserException("Invalid Password");
        }

        // Generate JWT
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        // Store token in Redis (TTL = 30 minutes)
        redisService.save(
                "TOKEN_" + user.getEmail(),
                token,
                30
        );

        return token;
    }
}
