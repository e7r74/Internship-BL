package com.bitlab.internship.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequestDto {
    @NotBlank(message = "Refresh token must not be blank")
    private String refreshToken;
}
