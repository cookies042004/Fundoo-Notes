package com.fundoonotes.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    
    //   UC4:User Registration DTO with validation rules
    
    @NotBlank(message = "First name is mandatory")
    @Pattern(regexp = "^[A-Z][a-zA-Z]{2,}$", message = "First name must start with a capital letter and be at least 3 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is mandatory")
    @Pattern(regexp = "^[A-Z][a-zA-Z]{2,}$", message = "Last name must start with a capital letter and be at least 3 characters")
    private String lastName;
    
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$", message = "Password must be at least 8 chars long with 1 upper, 1 lower, 1 digit, and 1 special char")
    private String password;
}
