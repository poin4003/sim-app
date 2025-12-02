package com.sim.app.sim_app.features.user.service;

import com.sim.app.sim_app.core.dto.PaginationResponse;
import com.sim.app.sim_app.features.user.service.schema.command.UserCreationCmd;
import com.sim.app.sim_app.features.user.service.schema.query.UserQuery;
import com.sim.app.sim_app.features.user.service.schema.result.UserResult;

public interface UserService {

    void checkEmailUnique(String email);

    UserResult createUser(UserCreationCmd cmd);

    PaginationResponse<UserResult> getManyUsers(UserQuery queryInput);
}
