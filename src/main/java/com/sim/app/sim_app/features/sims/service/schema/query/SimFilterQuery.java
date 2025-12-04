package com.sim.app.sim_app.features.sims.service.schema.query;

import com.sim.app.sim_app.base.BaseQuery;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class SimFilterQuery extends BaseQuery {
    private String phoneNumber;
    
    private Integer status;

    private Long fromDate;
    
    private Long toDate;
}
