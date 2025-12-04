package com.sim.app.sim_app.features.auth.service;

import com.sim.app.sim_app.features.auth.service.schema.command.LoginCmd;
import com.sim.app.sim_app.features.auth.service.schema.result.LoginResult;

public interface AuthService {
    LoginResult login(LoginCmd cmd);
}
