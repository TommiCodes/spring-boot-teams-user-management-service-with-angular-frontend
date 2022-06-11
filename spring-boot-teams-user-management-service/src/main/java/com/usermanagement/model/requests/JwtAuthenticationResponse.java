package com.usermanagement.model.requests;

import com.usermanagement.model.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private RefreshToken refreshToken;
    private String tokenType;
}