package com.usermanagement.config.security;

import com.usermanagement.model.Privilege;
import com.usermanagement.model.User;
import com.usermanagement.model.UserTeam;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private final long validityInMilliseconds = 3600000; // 1h

    private final MyUserDetailsService myUserDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(Long.toString(user.getId()));

        claims.put("email", user.getEmail());
        claims.put("auth", getAuthorities(user.getTeams()));
        claims.put("firstname", user.getFirstname());
        claims.put("lastname", user.getLastname());
        claims.put("username", user.getUsername());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Instead of the username we use the ID of the user (subject)
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Expired or invalid JWT token");
        }
    }

    private List<String> getAuthorities(List<UserTeam> userTeams) {
        List<String> authorities = new ArrayList<>();

        for (UserTeam userTeam : userTeams) {

            for (Privilege privilege : userTeam.getRole().getPrivileges()) {
                authorities.add("TEAM:" + userTeam.getTeam().getId() + "::" + "AUTHORITY:" + privilege.getPrivilege());
            }
        }

        return authorities;
    }

}
