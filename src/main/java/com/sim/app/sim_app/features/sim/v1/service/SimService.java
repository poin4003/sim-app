package com.sim.app.sim_app.features.sim.v1.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.sim.app.sim_app.core.dto.PaginationResponse;
import com.sim.app.sim_app.features.sim.v1.dto.request.CreateSimRequest;
import com.sim.app.sim_app.features.sim.v1.dto.response.SimExcelExportModel;
import com.sim.app.sim_app.features.sim.v1.dto.response.SimResponse;

import java.io.IOException;

public interface SimService {

    SimResponse createSim(CreateSimRequest request);

    SimResponse getSimById(UUID id);

    PaginationResponse<SimResponse> getManySim(long page, long size);

    List<SimExcelExportModel> getAllSimsForExcelExport();

    void processExcelDataAnalytics() throws InterruptedException;

    void importSimsFromExcel(MultipartFile file) throws IOException;
}
