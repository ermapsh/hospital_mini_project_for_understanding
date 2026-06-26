package com.ermapsh.hospital.controller;

import com.ermapsh.hospital.dto.*;
import com.ermapsh.hospital.service.AuthService;
import com.ermapsh.hospital.service.JwtService;
import com.ermapsh.hospital.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("signup")
    private SignupResponse signup(@RequestBody @Valid SignupRequest request){
        return userService.signUp(request);
    }

    @PostMapping("login")
    private ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(request));
    }

    @PostMapping("refresh")
    private ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request){
        String refreshToken = request.refreshToken();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authService.refreshToken(refreshToken));
    }

}
