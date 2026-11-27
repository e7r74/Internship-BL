package com.bitlab.internship.service.impl;

import com.bitlab.internship.dto.auth.LoginRequestDto;
import com.bitlab.internship.dto.auth.RefreshTokenRequestDto;
import com.bitlab.internship.dto.auth.TokenResponseDto;
import com.bitlab.internship.mappers.AuthMapper;
import com.bitlab.internship.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    private final RestClient restClient = RestClient.create();

    @Value("${keycloak.token-uri}")
    private String tokenUri;

    @Value("${keycloak.resource-client}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Override
    public TokenResponseDto login(LoginRequestDto request) {
        log.info("Authenticating user with username: {}", request.getUsername());

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", request.getUsername());
        formData.add("password", request.getPassword());

        try {
            log.debug("Sending token request to Keycloak URI: {}", tokenUri);
            Map<String, Object> response = restClient.post()
                    .uri(tokenUri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            log.info("User {} authenticated successfully", request.getUsername());
            return authMapper.toTokenResponse(response);
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.BadRequest ex) {
            log.error("Authentication failed for user: {}. Reason: {}", request.getUsername(), ex.getMessage());
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public TokenResponseDto refreshToken(RefreshTokenRequestDto requestDto) {
       log.info("Refreshing access token using refresh token");

       MultiValueMap<String, String> formData=new LinkedMultiValueMap<>();
       formData.add("grant_type", "refresh_token");
       formData.add("client_id", clientId);
       formData.add("client_secret", clientSecret);
       formData.add("refresh_token", requestDto.getRefreshToken());

       try {
           log.debug("Sending refresh token request to Keycloak URI: {}", tokenUri);
            Map<String, Object> response= restClient.post()
                    .uri(tokenUri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            log.info("access token refreshed successfully");
            return authMapper.toTokenResponse(response);
       }catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.BadRequest ex){
           log.error("Token refresh failed. Reason: {}", ex.getMessage());
            throw new BadCredentialsException("Invalid or expired refresh token");
       }
    }
}