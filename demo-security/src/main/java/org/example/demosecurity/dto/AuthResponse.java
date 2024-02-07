package org.example.demosecurity.dto;

import lombok.Getter;

@Getter
public class AuthResponse {

    private final Long id;
    private final String email;
    private final String token;

    public AuthResponse(Long id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }
}
