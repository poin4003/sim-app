package com.sim.app.sim_app.config.security.crypto;

import java.security.Key;

import com.sim.app.sim_app.config.security.crypto.dto.KeyPairDto;

import io.jsonwebtoken.SignatureAlgorithm;

public interface CryptoStrategy {
    
    KeyPairDto generateKey();

    Key getSigningKey(String keyStr);

    Key getVerifyingKey(String keyStr);

    SignatureAlgorithm getAlgorithm();
}
