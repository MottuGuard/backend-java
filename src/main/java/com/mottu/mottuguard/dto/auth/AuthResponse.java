package com.mottu.mottuguard.dto.auth;

public class AuthResponse {
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private String email;
    private String name;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(String token, String refreshToken, String email, String name) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.email = email;
        this.name = name;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
