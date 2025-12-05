package com.sim.app.sim_app.features.auth.api.dto;

import org.mapstruct.ReportingPolicy;

import com.sim.app.sim_app.config.mapstruct.GlobalMapperConfig;
import com.sim.app.sim_app.features.auth.api.dto.response.LoginResponse;
import com.sim.app.sim_app.features.auth.service.schema.result.LoginResult;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = { GlobalMapperConfig.class }
)
public interface AuthMapStruct {
    LoginResponse toLoginResponse(LoginResult result);
}
