package com.bitlab.internship.config;

import com.bitlab.internship.controller.AuthUserController;
import com.bitlab.internship.dto.auth.LoginRequestDto;
import com.bitlab.internship.dto.auth.RefreshTokenRequestDto;
import com.bitlab.internship.dto.auth.TokenResponseDto;
import com.bitlab.internship.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthUserController.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void loginEndpoint_WithoutToken_IsPermitted() throws Exception {
        LoginRequestDto request = new LoginRequestDto();
        request.setUsername("admin_user");
        request.setPassword("12345");

        when(authService.login(any())).thenReturn(TokenResponseDto.builder()
                .accessToken("jwt-token")
                .build());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void meEndpoint_WithoutToken_Returns401Unauthorized() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void meEndpoint_WithValidJwt_ExtractsRealmRolesAndReturns200() throws Exception {
        Jwt mockJwt = Jwt.withTokenValue("mock-token")
                .header("alg", "RS256")
                .claim("preferred_username", "admin_user")
                .claim("realm_access", Map.of("roles", List.of("ROLE_ADMIN")))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(300))
                .build();

        when(jwtDecoder.decode("mock-token")).thenReturn(mockJwt);

        mockMvc.perform(get("/api/auth/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer mock-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin_user"))
                .andExpect(jsonPath("$.authorities[0]").value("ROLE_ADMIN"));
    }
    @Test
    void refreshEndpoint_WithoutToken_IsPermitted() throws Exception {
        RefreshTokenRequestDto request = new RefreshTokenRequestDto("valid-refresh-token");

        when(authService.refreshToken(any())).thenReturn(TokenResponseDto.builder()
                .accessToken("new-jwt-token")
                .build());

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}