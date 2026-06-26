package com.ermapsh.hospital.service;


import com.ermapsh.hospital.dto.LoginDto;
import com.ermapsh.hospital.dto.LoginResponse;
import com.ermapsh.hospital.dto.RefreshTokenResponse;
import com.ermapsh.hospital.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public LoginResponse login(LoginDto loginDto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        User user = (User) auth.getPrincipal();
        assert user != null;
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponse(user.getId(), accessToken, refreshToken) ;
    }

    public RefreshTokenResponse refreshToken(String refreshToken) {
        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }
        Long userId = jwtService.getUserIdFromJwtToken(refreshToken);
        User user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        return new RefreshTokenResponse(accessToken, refreshToken);
    }
}
