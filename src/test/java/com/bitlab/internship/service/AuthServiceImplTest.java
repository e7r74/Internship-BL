package com.bitlab.internship.service;

import com.bitlab.internship.dto.LoginRequestDto;
import com.bitlab.internship.dto.TokenResponseDto;
import com.bitlab.internship.mappers.AuthMapper;
import com.bitlab.internship.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class AuthServiceImplTest {

    private AuthServiceImpl authService;
    private MockRestServiceServer mockServer;

    private final String tokenUri = "http://localhost:8081/realms/internship-realm/protocol/openid-connect/token";

    @BeforeEach
    void setUp() {
        AuthMapper authMapper = Mappers.getMapper(AuthMapper.class);
        authService = new AuthServiceImpl(authMapper);

        RestClient.Builder builder = RestClient.builder();
        mockServer = MockRestServiceServer.bindTo(builder).build();

        ReflectionTestUtils.setField(authService, "restClient", builder.build());
        ReflectionTestUtils.setField(authService, "tokenUri", tokenUri);
        ReflectionTestUtils.setField(authService, "clientId", "internship-client");
        ReflectionTestUtils.setField(authService, "clientSecret", "test-secret");
    }

    @Test
    void login_Success_ReturnsTokens() {
        LoginRequestDto request = new LoginRequestDto();
        request.setUsername("admin_user");
        request.setPassword("12345");

        String keycloakJsonResponse = """
                {
                  "access_token": "mock-jwt-access-token",
                  "refresh_token": "mock-refresh-token",
                  "expires_in": 300,
                  "refresh_expires_in": 604800,
                  "token_type": "Bearer"
                }
                """;

        mockServer.expect(requestTo(tokenUri))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED))
                .andRespond(withSuccess(keycloakJsonResponse, MediaType.APPLICATION_JSON));

        TokenResponseDto response = authService.login(request);

        assertNotNull(response);
        assertEquals("mock-jwt-access-token", response.getAccessToken());
        assertEquals("mock-refresh-token", response.getRefreshToken());
        assertEquals(300L, response.getExpiresIn());
        assertEquals(604800L, response.getRefreshExpiresIn());
        assertEquals("Bearer", response.getTokenType());
        mockServer.verify();
    }

    @Test
    void login_InvalidCredentials_ThrowsBadCredentialsException() {
        LoginRequestDto request = new LoginRequestDto();
        request.setUsername("admin_user");
        request.setPassword("wrong_password");

        mockServer.expect(requestTo(tokenUri))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.UNAUTHORIZED));

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid username or password", exception.getMessage());
        mockServer.verify();
    }
}