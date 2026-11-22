package com.bitlab.internship.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthUserController {
    @GetMapping("/me")
    public Map<String,Object> currentUser(Authentication authentication){
        User user=(User) authentication.getPrincipal();
        List<String> authorities=user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Map.of(
                "username", user.getUsername(),
                "authorities", authorities
        );
    }
}
