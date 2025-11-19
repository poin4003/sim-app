package com.sim.app.sim_app.features.sims.service.schema.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimFilterQuery {
    private String phoneNumber;
    
    private Integer status;

    private Long fromDate;
    
    private Long toDate;

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 20;
}
