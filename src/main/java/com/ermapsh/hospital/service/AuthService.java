package com.ermapsh.hospital.service;


import com.ermapsh.hospital.dto.LoginDto;
import com.ermapsh.hospital.dto.LoginResponse;
import com.ermapsh.hospital.dto.RefreshTokenResponse;
import com.ermapsh.hospital.entity.Session;
import com.ermapsh.hospital.entity.User;
import com.nimbusds.jwt.proc.ExpiredJWTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    public LoginResponse login(LoginDto loginDto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        log.warn(String.valueOf(auth));

        User user = (User) auth.getPrincipal();
        log.warn(String.valueOf(user));
        assert user != null;
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user, refreshToken);

        return new LoginResponse(user.getId(), accessToken, refreshToken);
    }

    public RefreshTokenResponse refreshToken(String refreshToken) throws ExpiredJWTException {
        log.warn("before -----1");
        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        log.warn("before -----2");

        sessionService.validateSession(refreshToken);

        Long userId = jwtService.getUserIdFromJwtToken(refreshToken);
        log.warn("before -----3 and log: "+ userId);
        User user = userService.getUserById(userId);
        log.warn("before -----3");
        String accessToken = jwtService.generateAccessToken(user);
        return new RefreshTokenResponse(userId, accessToken, refreshToken);
    }

    public Session logout(String refreshToken){
        return sessionService.deleteSession(refreshToken);
    }
}
