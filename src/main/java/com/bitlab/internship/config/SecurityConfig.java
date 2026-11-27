package com.bitlab.internship.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${keycloak.resource-client:internship-client}")
    private String resourceClient;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                        .requestMatchers("/api/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtToSpringUserAuthenticationConverter()))
                );

        return http.build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtToSpringUserAuthenticationConverter() {
        return jwt -> {
            Collection<GrantedAuthority> authorities = extractAuthorities(jwt);

            String username = jwt.getClaimAsString("preferred_username");
            if (username == null || username.isBlank()) {
                username = jwt.getSubject();
            }

            User springUser = new User(username, "", authorities);
            return new UsernamePasswordAuthenticationToken(springUser, jwt.getTokenValue(), authorities);
        };
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Set<String> roles = new LinkedHashSet<>();

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null && realmAccess.get("roles") instanceof Collection<?> realmRoles) {
            realmRoles.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .forEach(roles::add);
        }

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null && resourceAccess.get(resourceClient) instanceof Map<?, ?> clientAccess) {
            if (clientAccess.get("roles") instanceof Collection<?> clientRoles) {
                clientRoles.stream()
                        .filter(Objects::nonNull)
                        .map(Object::toString)
                        .forEach(roles::add);
            }
        }

        return roles.stream()
                .map(String::trim)
                .filter(r -> !r.isEmpty())
                .map(r -> r.startsWith("ROLE_") ? r.toUpperCase(Locale.ROOT) : "ROLE_" + r.toUpperCase(Locale.ROOT))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}