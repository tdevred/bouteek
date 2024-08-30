package com.tdevred.bouteek.authentication;

import com.tdevred.bouteek.authentication.services.AuthenticationService;
import com.tdevred.bouteek.authentication.DTOs.LoginResponse;
import com.tdevred.bouteek.authentication.DTOs.LoginUserDto;
import com.tdevred.bouteek.authentication.DTOs.RegisterUserDto;
import com.tdevred.bouteek.authentication.services.JwtService;
import com.tdevred.bouteek.authentication.entities.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"},methods = {RequestMethod.POST})
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setUsername(authenticatedUser.getFullName());

        return ResponseEntity.ok(loginResponse);
    }
}