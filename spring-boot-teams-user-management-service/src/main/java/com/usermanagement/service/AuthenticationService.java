package com.usermanagement.service;

import com.usermanagement.config.security.JwtTokenProvider;
import com.usermanagement.model.RefreshToken;
import com.usermanagement.model.User;
import com.usermanagement.repository.RefreshTokenRepository;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.requests.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Login in a user by email and password.
     * Password is checked against the password hash in the database
     */
    public JwtAuthenticationResponse login(String email, String plainPassword) {
        User user = userService.findByEmail(email);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getId(), plainPassword));
        String jwtToken = jwtTokenProvider.createToken(user);
        RefreshToken refreshToken = createRefreshToken(user.getId());
        return JwtAuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .tokenType("BEARER")
                .build();
    }

    /*
     * Here we handle all the stuff for refresh tokens
     */
    public RefreshToken createRefreshToken(Long userId) {
        // TODO: Replace hardcoded value by property
        // @Value("${security.token.refresh.duration}")
        long refreshTokenDurationMs = 11111L;

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new login request");
        }
        return token;
    }

}
