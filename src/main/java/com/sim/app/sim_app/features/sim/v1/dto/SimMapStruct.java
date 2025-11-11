package com.sim.app.sim_app.features.sim.v1.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.sim.app.sim_app.config.mapstruct.GlobalMapperConfig;
import com.sim.app.sim_app.features.sim.entity.SimEntity;
import com.sim.app.sim_app.features.sim.enums.SimStatusEnum;
import com.sim.app.sim_app.features.sim.v1.dto.request.CreateSimRequest;
import com.sim.app.sim_app.features.sim.v1.dto.request.SimExcelImportModel;
import com.sim.app.sim_app.features.sim.v1.dto.response.SimExcelExportModel;
import com.sim.app.sim_app.features.sim.v1.dto.response.SimResponse;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = { GlobalMapperConfig.class }
)
public interface SimMapStruct {

    SimResponse toResponseDto(SimEntity entity);

    SimEntity toEntity(CreateSimRequest request);

    @Mapping(target = "simId", source = "simId", qualifiedByName = "uuidToString")
    @Mapping(target = "simStatus", source = "simStatus", qualifiedByName = "enumToString")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "localDateTimeToString")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "localDateTimeToString")
    SimExcelExportModel toExcelModel(SimEntity entity);
    
    @Mapping(target = "simStatus", source = "simStatusString", qualifiedByName = "mapStatusString")
    CreateSimRequest toCreateRequest(SimExcelImportModel model);

    @Named("mapStatusString") 
    default SimStatusEnum mapSimStatusString(String statusString) {
        if (statusString == null || statusString.trim().isEmpty()) {
            return null; 
        }
        
        try {
            return SimStatusEnum.valueOf(statusString.trim().toUpperCase()); 
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                String.format("Invalid SimStatus value '%s'. Expected values: %s", 
                               statusString, java.util.Arrays.toString(SimStatusEnum.values()))
            );
        }
    }
}
