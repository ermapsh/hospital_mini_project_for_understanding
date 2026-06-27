package com.ermapsh.hospital.dto;

public record RefreshTokenResponse(
        Long id,
        String accessToken,
        String refreshToken
){}
