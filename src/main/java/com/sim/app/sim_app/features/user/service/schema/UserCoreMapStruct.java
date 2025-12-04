package com.sim.app.sim_app.features.user.service.schema;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.sim.app.sim_app.config.mapstruct.GlobalMapperConfig;
import com.sim.app.sim_app.features.user.entity.UserBaseEntity;
import com.sim.app.sim_app.features.user.service.schema.result.UserResult;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = { GlobalMapperConfig.class }
)
public interface UserCoreMapStruct {

    UserResult toUserResult(UserBaseEntity userBase);
}
