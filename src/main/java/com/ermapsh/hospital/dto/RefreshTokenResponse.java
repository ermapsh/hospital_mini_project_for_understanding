package com.ermapsh.hospital.dto;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
){}
