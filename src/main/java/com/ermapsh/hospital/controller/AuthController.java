package com.ermapsh.hospital.controller;

import com.ermapsh.hospital.dto.*;
import com.ermapsh.hospital.service.AuthService;
import com.ermapsh.hospital.service.JwtService;
import com.ermapsh.hospital.service.UserService;
import com.nimbusds.jwt.proc.ExpiredJWTException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    private ResponseEntity<ApiResonse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) throws ExpiredJWTException {
        String refreshToken = request.refreshToken();
        return ResponseEntity.status(202).body(new ApiResonse("refresh token",authService.refreshToken(refreshToken)));
    }

    @DeleteMapping("logout")
    private ResponseEntity<?> logout(@RequestBody @Valid RefreshTokenRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authService.logout(request.refreshToken()));
    }

    @GetMapping("user/{userId}")
    private ResponseEntity<?> getByUserId(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getUserById(userId));
    }

    @GetMapping("user/email/{emailId}")
    private ResponseEntity<?> getByUserEmail(@PathVariable String emailId){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getUserByEmail(emailId));
    }

    @PutMapping("user/update/{emailId}")
    private ResponseEntity<?> updateByUserEmail(@PathVariable String emailId){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(emailId));
    }

    @DeleteMapping("user/delete/{emailId}")
    private ResponseEntity<?> deleteByUserEmail(@PathVariable String emailId){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.deleteUser(emailId)? "Deleted": null);
    }


}
