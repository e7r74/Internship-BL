package com.bitlab.internship.service;

import com.bitlab.internship.dto.LoginRequestDto;
import com.bitlab.internship.dto.TokenResponseDto;

public interface AuthService {
    TokenResponseDto login(LoginRequestDto requestDto);
}
