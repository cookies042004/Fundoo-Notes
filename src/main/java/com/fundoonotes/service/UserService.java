package com.fundoonotes.service;

import com.fundoonotes.dto.request.UserLoginDTO;
import com.fundoonotes.dto.request.UserRegistrationDTO;
import com.fundoonotes.entity.User;

public interface UserService {
    // [Prajwal]:UC4:Service interface for user registration decoupling implementation
    User registerUser(UserRegistrationDTO dto);

    // [Prajwal]:UC5:Service interface for login processing
    String loginUser(UserLoginDTO dto);
}
