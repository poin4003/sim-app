package com.sim.app.sim_app.features.sim.v1.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.sim.app.sim_app.features.sim.entity.SimEntity;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SimMapStruct {

    SimResponse toResponseDto(SimEntity entity);

    SimEntity toEntity(CreateSimRequest request);
}
