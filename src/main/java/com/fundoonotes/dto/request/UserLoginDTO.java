package com.fundoonotes.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
    //   UC5:User Login DTO
    
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Password is mandatory")
    private String password;
}
