package com.sim.app.sim_app.features.auth.service.schema;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.sim.app.sim_app.config.mapstruct.GlobalMapperConfig;
import com.sim.app.sim_app.features.auth.entity.KeyStoreEntity;
import com.sim.app.sim_app.features.auth.service.schema.result.KeyStoreResult;
import com.sim.app.sim_app.features.auth.service.schema.result.LoginResult;
import com.sim.app.sim_app.features.auth.service.schema.result.UserPrincipal;
import com.sim.app.sim_app.features.user.entity.UserBaseEntity;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = { GlobalMapperConfig.class }
)
public interface AuthCoreMapStruct {

    @Mapping(target = "authorities", expression = "java(userBase.getAuthorities())")
    UserPrincipal toUserPrincipal(UserBaseEntity userBase);

    KeyStoreResult toKeyStoreResult(KeyStoreEntity keyStore);

    LoginResult toLoginResult(
        String accessToken, 
        String refreshToken,
        UUID userId,
        String userEmail
    );
}
