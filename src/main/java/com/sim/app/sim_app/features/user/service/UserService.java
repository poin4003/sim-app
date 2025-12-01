package com.sim.app.sim_app.features.user.service;

import com.sim.app.sim_app.features.user.service.schema.command.UserCreationCmd;
import com.sim.app.sim_app.features.user.service.schema.result.UserResult;

public interface UserService {

    public void checkEmailUnique(String email);

    public UserResult createUser(UserCreationCmd cmd);
}
