package com.sim.app.sim_app.features.auth.service;

import java.util.UUID;

import com.sim.app.sim_app.features.auth.service.schema.command.LoginCmd;
import com.sim.app.sim_app.features.auth.service.schema.result.LoginResult;

public interface AuthService {
    LoginResult login(LoginCmd cmd);

    LoginResult refreshToken(String refreshToken);

    void logout(UUID keyStoreId, UUID userId);
}
