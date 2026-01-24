package com.student.portal.Auth;

import lombok.Data;

@Data
public class AuthResponse {
    private String username;
    private String accessToken;
    private String role;
    private String id;

    public AuthResponse() {
    }

    public AuthResponse(String username, String accessToken, String role, String id) {
        this.username = username;
        this.accessToken = accessToken;
        this.role = role;
        this.id = id;
    }
}

