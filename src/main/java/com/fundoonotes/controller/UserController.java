package com.fundoonotes.controller;

import com.fundoonotes.dto.request.UserLoginDTO;
import com.fundoonotes.dto.request.UserRegistrationDTO;
import com.fundoonotes.dto.response.ResponseDTO;
import com.fundoonotes.entity.User;
import com.fundoonotes.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // [Prajwal]:UC4:Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO userDTO) {
        log.info("Received request to register user: {}", userDTO.getEmail());
        User user = userService.registerUser(userDTO);
        ResponseDTO responseDTO = new ResponseDTO("User registered successfully", user.getId());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    // [Prajwal]:UC5:Endpoint for user login returning JWT Token
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@Valid @RequestBody UserLoginDTO loginDTO) {
        log.info("Received request to login user: {}", loginDTO.getEmail());
        String token = userService.loginUser(loginDTO);
        
        // [Prajwal]:UC5:Provide token string mapping back to frontend
        ResponseDTO responseDTO = new ResponseDTO("User logged in successfully", token);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
