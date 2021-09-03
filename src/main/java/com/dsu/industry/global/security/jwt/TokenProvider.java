package com.dsu.industry.global.security.jwt;

import com.dsu.industry.global.config.AppProperties;
import com.dsu.industry.global.security.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;


@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider {

    private final AppProperties appProperties;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - 유효하지 않은 JWT 서명");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - 유효하지 않은 JWT 토큰");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - 만료된 JWT 토큰");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - 지원하지 않는 JWT 토큰");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - 비어있는 JWT");
        }
        return false;
    }

}
