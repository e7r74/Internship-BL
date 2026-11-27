package com.bitlab.internship.controller;

import com.bitlab.internship.dto.auth.LoginRequestDto;
import com.bitlab.internship.dto.auth.RefreshTokenRequestDto;
import com.bitlab.internship.dto.auth.TokenResponseDto;
import com.bitlab.internship.exception.GlobalExceptionHandler;
import com.bitlab.internship.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthUserControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthUserController authUserController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authUserController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void login_ValidRequest_Returns200AndTokens() throws Exception {
        LoginRequestDto request = new LoginRequestDto();
        request.setUsername("admin_user");
        request.setPassword("12345");

        TokenResponseDto response = TokenResponseDto.builder()
                .accessToken("jwt-token")
                .refreshToken("refresh-token")
                .expiresIn(300L)
                .refreshExpiresIn(36000L)
                .tokenType("Bearer")
                .build();

        when(authService.login(any(LoginRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("jwt-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"))
                .andExpect(jsonPath("$.expiresIn").value(300));
    }

    @Test
    void login_BlankFields_Returns400BadRequest() throws Exception {
        LoginRequestDto request = new LoginRequestDto();
        request.setUsername("");
        request.setPassword("");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void login_WrongPassword_Returns401Unauthorized() throws Exception {
        LoginRequestDto request = new LoginRequestDto();
        request.setUsername("admin_user");
        request.setPassword("wrong");

        when(authService.login(any(LoginRequestDto.class)))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid username or password"))
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void refreshToken_ValidRequest_Returns200AndNewTokens() throws Exception {
        RefreshTokenRequestDto request = new RefreshTokenRequestDto("valid-refresh-token");

        TokenResponseDto response = TokenResponseDto.builder()
                .accessToken("new-jwt-token")
                .refreshToken("new-refresh-token")
                .expiresIn(300L)
                .refreshExpiresIn(36000L)
                .tokenType("Bearer")
                .build();

        when(authService.refreshToken(any(RefreshTokenRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("new-jwt-token"))
                .andExpect(jsonPath("$.refreshToken").value("new-refresh-token"));
    }

    @Test
    void refreshToken_BlankToken_Returns400BadRequest() throws Exception {
        RefreshTokenRequestDto request = new RefreshTokenRequestDto("");

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void refreshToken_ExpiredToken_Returns401Unauthorized() throws Exception {
        RefreshTokenRequestDto request = new RefreshTokenRequestDto("expired-token");

        when(authService.refreshToken(any(RefreshTokenRequestDto.class)))
                .thenThrow(new BadCredentialsException("Invalid or expired refresh token"));

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid or expired refresh token"))
                .andExpect(jsonPath("$.status").value(401));
    }
}