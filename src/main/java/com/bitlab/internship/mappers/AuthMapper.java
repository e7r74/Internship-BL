package com.bitlab.internship.mappers;

import com.bitlab.internship.dto.auth.TokenResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "accessToken", expression = "java(asString(keycloakResponse.get(\"access_token\")))")
    @Mapping(target = "refreshToken", expression = "java(asString(keycloakResponse.get(\"refresh_token\")))")
    @Mapping(target = "expiresIn", expression = "java(asLong(keycloakResponse.get(\"expires_in\")))")
    @Mapping(target = "refreshExpiresIn", expression = "java(asLong(keycloakResponse.get(\"refresh_expires_in\")))")
    @Mapping(target = "tokenType", expression = "java(asString(keycloakResponse.get(\"token_type\")))")
    TokenResponseDto toTokenResponse(Map<String, Object> keycloakResponse);

    default String asString(Object value) {
        return value != null ? value.toString() : null;
    }

    default Long asLong(Object value) {
        return value instanceof Number number ? number.longValue() : null;
    }
}