package com.tdevred.bouteek.authentication.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterUserDto {
    @Email(message = "Invalid email")
    @NotNull(message = "No email provided")
    private String email;

    @NotBlank(message = "Invalid password")
    @NotNull(message = "No password provided")
    private String password;

    @NotBlank(message = "Invalid username")
    @NotNull(message = "No username provided")
    private String fullName;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
