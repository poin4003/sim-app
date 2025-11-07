package com.sim.app.sim_app.features.sim.v1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateSimRequest {
    
    @NotBlank
    private String simPhoneNumber;

    @NotNull
    private Integer simStatus;

    private Integer simSellingPrice;
    private Integer simDealerPrice;
    private Integer simImportPrice;
}