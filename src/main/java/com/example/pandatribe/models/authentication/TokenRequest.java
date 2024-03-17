package com.example.pandatribe.models.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequest {
    String grant_type;
    String client_id;
    String client_secret;
}
