package com.bitlab.internship.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private Long refreshExpiresIn;
    private String tokenType;
}
