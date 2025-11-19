package com.sim.app.sim_app.features.sims.service.schema.command;

import com.sim.app.sim_app.features.sims.enums.SimStatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimCmd {
    private String simPhoneNumber;

    private Integer simImportPrice;

    private Integer simSellingPrice;

    private Integer simDealerPrice;

    private SimStatusEnum simStatus; 
}
