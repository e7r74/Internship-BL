package com.bitlab.internship.mapper;

import com.bitlab.internship.dto.TokenResponseDto;
import com.bitlab.internship.mappers.AuthMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AuthMapperTest {

    private final AuthMapper authMapper = Mappers.getMapper(AuthMapper.class);

    @Test
    void toTokenResponse_ValidMap_MapsAllFieldsCorrectly() {
        Map<String, Object> keycloakResponse = Map.of(
                "access_token", "eyJhbGciOiJSUzI1NiJ9.access",
                "refresh_token", "eyJhbGciOiJIUzI1NiJ9.refresh",
                "expires_in", 300,
                "refresh_expires_in", 604800L,
                "token_type", "Bearer"
        );

        TokenResponseDto result = authMapper.toTokenResponse(keycloakResponse);

        assertNotNull(result);
        assertEquals("eyJhbGciOiJSUzI1NiJ9.access", result.getAccessToken());
        assertEquals("eyJhbGciOiJIUzI1NiJ9.refresh", result.getRefreshToken());
        assertEquals(300L, result.getExpiresIn());
        assertEquals(604800L, result.getRefreshExpiresIn());
        assertEquals("Bearer", result.getTokenType());
    }

    @Test
    void toTokenResponse_NullMap_ReturnsNull() {
        TokenResponseDto result = authMapper.toTokenResponse(null);

        assertNull(result);
    }

    @Test
    void toTokenResponse_MissingOrInvalidFields_HandlesGracefully() {
        Map<String, Object> partialResponse = new HashMap<>();
        partialResponse.put("access_token", "only-access-token");
        partialResponse.put("expires_in", "not-a-number");
        partialResponse.put("refresh_token", null);

        TokenResponseDto result = authMapper.toTokenResponse(partialResponse);

        assertNotNull(result);
        assertEquals("only-access-token", result.getAccessToken());
        assertNull(result.getRefreshToken());
        assertNull(result.getExpiresIn());
        assertNull(result.getRefreshExpiresIn());
        assertNull(result.getTokenType());
    }
}