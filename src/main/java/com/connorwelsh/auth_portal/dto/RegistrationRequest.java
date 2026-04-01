package com.connorwelsh.auth_portal.dto;

import com.connorwelsh.auth_portal.validation.StrongPassword;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @StrongPassword
    private String password;

    @NotBlank(message = "Password confirmation is required")
    private String confirmPassword;
    
}
