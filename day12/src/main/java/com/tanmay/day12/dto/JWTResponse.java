package com.tanmay.day12.dto;

import lombok.Getter;

@Getter
public class JWTResponse {
    private final String token;

    public JWTResponse(String token) {
        this.token = token;
    }
}
