package com.sim.app.sim_app.base;

import lombok.Data;

@Data
public class BaseQuery {
    
    private Integer pageSize = 10;

    private Integer currentPage = 1;
    
}
