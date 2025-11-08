package com.sim.app.sim_app.features.sim.v1.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class SimResponse {
    private UUID simId;

    private String simPhoneNumber;
    private Integer simStatus;
    private Integer simSellingPrice;
    private Integer simDealerPrice;
    private Integer simImportPrice;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
