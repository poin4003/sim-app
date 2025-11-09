package com.sim.app.sim_app.features.sim.v1.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sim.app.sim_app.features.sim.enums.SimStatusEnum;

import lombok.Data;

@Data
public class SimResponse {
    private UUID simId;

    private String simPhoneNumber;
    private SimStatusEnum simStatus;
    private Integer simSellingPrice;
    private Integer simDealerPrice;
    private Integer simImportPrice;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
