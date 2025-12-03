package com.sim.app.sim_app.config.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sim.app.sim_app.config.jwt.dto.JwtPayload;
import com.sim.app.sim_app.config.jwt.dto.JwtProperties;
import com.sim.app.sim_app.config.jwt.dto.KeyPairDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;

    public KeyPairDto generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();

            String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
            String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());

            return new KeyPairDto(privateKey, publicKey);
        } catch (Exception e) {
            throw new RuntimeException("Error generating RSA keys", e);
        }
    }

    private PrivateKey getPrivateKeyFromString(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public String generateAccessToken(UUID userId, JwtPayload payload, String privateKeyStr) {
        try {
            long now = System.currentTimeMillis();
            long expiryDate = now + jwtProperties.getAccessTokenExpirationMs(); 

            PrivateKey privateKey = getPrivateKeyFromString(privateKeyStr);

            Map<String, Object> claims = objectMapper.convertValue(payload, new TypeReference<Map<String, Object>>() {});

            return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId.toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiryDate))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
        } catch (Exception e) {
            throw new RuntimeException("Could not generate token", e);
        }
    }

    public String generateRefreshToken(UUID userId, String privateKeyStr) {
        try {
            long now = System.currentTimeMillis();
            long expiryDate = now + jwtProperties.getRefreshTokenExpirationMs();

            PrivateKey privateKey = getPrivateKeyFromString(privateKeyStr);

            return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiryDate))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
        } catch (Exception e) {
            throw new RuntimeException("Could not generate refresh token", e);
        }
    }

    public UUID getUserIdFromTokenUnverified(String token) {
        try {
            String tokenWithoutSignature = token.substring(0, token.lastIndexOf('.') + 1);
            Claims claims = Jwts.parserBuilder()
                                .build()
                                .parseClaimsJwt(tokenWithoutSignature)
                                .getBody();

            String sub = claims.getSubject();

            return UUID.fromString(sub);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token, String publicKeyStr) {
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            PublicKey publicKey = kf.generatePublic(spec);

            Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getAllClaimsFromToken(String token, String publicKeyStr) throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        PublicKey publicKey = kf.generatePublic(spec);

        return Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    } 
}
