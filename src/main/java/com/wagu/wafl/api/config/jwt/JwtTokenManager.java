package com.wagu.wafl.api.config.jwt;

import com.wagu.wafl.api.common.exception.AuthException;
import com.wagu.wafl.api.common.message.ExceptionMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bouncycastle.jcajce.BCFKSLoadStoreParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class JwtTokenManager {
    private final JwtConfig jwtConfig;
    private final ZoneId KST = ZoneId.of("Asia/Seoul");

    public String createAccessToken(Long userId) {
        val exp = ZonedDateTime.now(KST).toLocalDateTime().plusHours(3).atZone(KST).toInstant();
        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setExpiration(Date.from(exp))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean verifyToken(String token) {
        try {
            getBody(token);
            return true;
        } catch (ExpiredJwtException | SignatureException e) { // 만료된 토큰을 잡는데,
            throw new AuthException(ExceptionMessage.INVALID_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        final byte[] keyBytes = jwtConfig.getJwtSecretKey().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getJwtContents(String token) {
        final Claims claims = getBody(token);
        return (String) claims.get("sub"); // claims에 "sub"에 userId가 들어가는데 위에서 .subject를 수정해야 하나?
    }
}
