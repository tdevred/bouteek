package com.tdevred.bouteek.authentication.DTOs;

import lombok.Getter;
import lombok.Setter;

public class LoginResponse {
    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private long expiresIn;

    @Getter
    @Setter
    private String username;
}