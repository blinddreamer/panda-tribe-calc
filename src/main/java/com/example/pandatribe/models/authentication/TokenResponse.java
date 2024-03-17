package com.example.pandatribe.models.authentication;

import lombok.Data;

@Data
public class TokenResponse {
    private String access_token;
    private String token_type;
    private long expires_in;
}
