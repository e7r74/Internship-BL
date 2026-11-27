package com.bitlab.internship.controller;

import com.bitlab.internship.dto.auth.LoginRequestDto;
import com.bitlab.internship.dto.auth.RefreshTokenRequestDto;
import com.bitlab.internship.dto.auth.TokenResponseDto;
import com.bitlab.internship.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth User", description = "The current user taken from the Keycloak token")
public class AuthUserController {
    private final AuthService authService;
    public record CurrentUserResponse(String username, List<String> authorities) {}

    @GetMapping("/me")
    @Operation(summary = "Get the current user and its authorities (GrantedAuthority)")
    public ResponseEntity<CurrentUserResponse>  me(@AuthenticationPrincipal User user) {
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .toList();
        return ResponseEntity.ok(new CurrentUserResponse(user.getUsername(), authorities)) ;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user via Keycloak and return JWT and Refresh tokens")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto){
       TokenResponseDto tokenResponseDto= authService.login(requestDto);
       return ResponseEntity.ok(tokenResponseDto);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token", description = "Issues a new JWT access token and refresh token using a valid refresh token")
    public ResponseEntity<TokenResponseDto> refreshToken (@Valid @RequestBody RefreshTokenRequestDto requestDto){
        TokenResponseDto tokenResponseDto= authService.refreshToken(requestDto);
        return ResponseEntity.ok(tokenResponseDto);
    }
}