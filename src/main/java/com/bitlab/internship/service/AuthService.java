package com.bitlab.internship.service;

import com.bitlab.internship.dto.auth.LoginRequestDto;
import com.bitlab.internship.dto.auth.RefreshTokenRequestDto;
import com.bitlab.internship.dto.auth.TokenResponseDto;

public interface AuthService {
    TokenResponseDto login(LoginRequestDto requestDto);
    TokenResponseDto refreshToken(RefreshTokenRequestDto requestDto);
}
