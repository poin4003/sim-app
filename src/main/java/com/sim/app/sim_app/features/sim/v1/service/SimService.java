package com.sim.app.sim_app.features.sim.v1.service;

import java.util.UUID;

import com.sim.app.sim_app.core.dto.PaginationResponse;
import com.sim.app.sim_app.features.sim.v1.dto.CreateSimRequest;
import com.sim.app.sim_app.features.sim.v1.dto.SimResponse;

public interface SimService {
    SimResponse createSim(CreateSimRequest request);
    SimResponse getSimById(UUID id);
    PaginationResponse<SimResponse> getManySim(long page, long size);
}
