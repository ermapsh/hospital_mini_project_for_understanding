package com.ermapsh.hospital.controller;

import com.ermapsh.hospital.dto.LoginDto;
import com.ermapsh.hospital.dto.SignupRequest;
import com.ermapsh.hospital.dto.SignupResponse;
import com.ermapsh.hospital.service.AuthService;
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

    @PostMapping("signup")
    private SignupResponse signup(@RequestBody @Valid SignupRequest request){
        return userService.signUp(request);
    }

    @PostMapping("login")
    private ResponseEntity<String> login(@RequestBody @Valid LoginDto request){
        String token = authService.login(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                token
        );
    }

}
