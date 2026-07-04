package com.ermapsh.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


public record LoginResponse(
        Long id,
        String accessToken,
        String refreshToken
) {

}
