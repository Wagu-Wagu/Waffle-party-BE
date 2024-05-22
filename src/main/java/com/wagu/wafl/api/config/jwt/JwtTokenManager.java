package com.wagu.wafl.api.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bouncycastle.jcajce.BCFKSLoadStoreParameter;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class JwtTokenManager {
    private final JwtConfig jwtConfig;
    private final ZoneId KST = ZoneId.of("Asia/Seoul");

    public String createAccessToken(Long userId) {
        val signatureAlgorithm = SignatureAlgorithm.HS256;
        val secretKeyBytes = DatatypeConverter.parseBase64Binary(jwtConfig.getJwtSecretKey());
        val signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        val exp = ZonedDateTime.now(KST).toLocalDateTime().plusHours(3).atZone(KST).toInstant();

        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setExpiration(Date.from(exp))
                .signWith(signingKey, signatureAlgorithm)
                .compact();
    }
}
