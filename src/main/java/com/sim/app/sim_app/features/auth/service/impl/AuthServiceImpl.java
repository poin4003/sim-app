package com.sim.app.sim_app.features.auth.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sim.app.sim_app.config.jwt.JwtTokenProvider;
import com.sim.app.sim_app.config.jwt.dto.JwtPayload;
import com.sim.app.sim_app.config.jwt.dto.KeyPairDto;
import com.sim.app.sim_app.features.auth.entity.KeyStoreEntity;
import com.sim.app.sim_app.features.auth.repository.KeyStoreRepository;
import com.sim.app.sim_app.features.auth.service.AuthService;
import com.sim.app.sim_app.features.auth.service.schema.command.LoginCmd;
import com.sim.app.sim_app.features.auth.service.schema.result.LoginResult;
import com.sim.app.sim_app.features.user.entity.UserBaseEntity;
import com.sim.app.sim_app.features.user.repository.UserBaseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserBaseRepository userBaseRepository;
    private final KeyStoreRepository keyStoreRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResult login(LoginCmd cmd) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(cmd.getUserEmail(), cmd.getUserPassword())
        );

        UserBaseEntity userDetails = (UserBaseEntity) authentication.getPrincipal();
        UUID userId = userDetails.getUserId();
        String userEmail = userDetails.getUserEmail();

        KeyPairDto keyPair = jwtTokenProvider.generateKeyPair();
        String privateKey = keyPair.getPrivateKey();
        String publicKey = keyPair.getPublicKey();

        JwtPayload payload = JwtPayload.builder()
            .userEmail(userEmail)
            .build();

        String accessToken = jwtTokenProvider.generateAccessToken(userId, payload, privateKey);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId, privateKey);

        saveOrUpdateKeyStore(userId, publicKey, privateKey, refreshToken);
        updateUserLoginInfo(userId, cmd.getIpAddress());

        return LoginResult.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userId)
                .userEmail(userEmail)
                .build();
    }

    private void saveOrUpdateKeyStore(UUID userId, String publicKey, String privateKey, String refreshToken) {
        LambdaQueryWrapper<KeyStoreEntity> query = new LambdaQueryWrapper<>();
        query.eq(KeyStoreEntity::getUserId, userId);

        boolean isNew = false;

        KeyStoreEntity keyStore = keyStoreRepository.selectOne(query);
        if (keyStore == null) {
            isNew = true;
            keyStore = new KeyStoreEntity();
            keyStore.setKeyStoreId(UUID.randomUUID());
            keyStore.setUserId(userId);
        }

        keyStore.setPublicKey(publicKey);
        keyStore.setPrivateKey(privateKey);
        keyStore.setRefreshToken(refreshToken);

        if (isNew) {
            keyStoreRepository.insert(keyStore);
        } else {
            keyStoreRepository.updateById(keyStore);
        }
    }

    private void updateUserLoginInfo(UUID userId, String ipAddress) {
        UserBaseEntity userUpdate = new UserBaseEntity();
        userUpdate.setUserId(userId);
        userUpdate.setUserLoginTime(LocalDateTime.now());
        userUpdate.setUserLoginIp(ipAddress);

        userBaseRepository.updateById(userUpdate);
    }
}
