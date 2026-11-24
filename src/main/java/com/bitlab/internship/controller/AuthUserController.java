package com.bitlab.internship.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth User", description = "The current user taken from the Keycloak token")
public class AuthUserController {

    public record CurrentUserResponse(String username, List<String> authorities) {}

    @GetMapping("/me")
    @Operation(summary = "Get the current user and its authorities (GrantedAuthority)")
    public CurrentUserResponse me(@AuthenticationPrincipal User user) {
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .toList();
        return new CurrentUserResponse(user.getUsername(), authorities);
    }
}