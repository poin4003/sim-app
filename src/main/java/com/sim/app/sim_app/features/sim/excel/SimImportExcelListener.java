package com.sim.app.sim_app.features.sim.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.sim.app.sim_app.features.sim.producer.SimImportProducer;
import com.sim.app.sim_app.features.sim.v1.dto.SimMapStruct;
import com.sim.app.sim_app.features.sim.v1.dto.request.CreateSimRequest;
import com.sim.app.sim_app.features.sim.v1.dto.request.SimExcelImportModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SimImportExcelListener extends AnalysisEventListener<SimExcelImportModel> {
    
    private final SimImportProducer simImportProducer;
    private final SimMapStruct simMapStruct;

    @Override
    public void invoke(SimExcelImportModel data, AnalysisContext context) {
        log.info("Processing row: {}", context.readRowHolder().getRowIndex());

        CreateSimRequest request = simMapStruct.toCreateRequest(data);

        simImportProducer.sendSimToImportQueue(request);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("--- Excel import finished. All SIMs sent to Kafka queue. ---");
    }
    
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("Error reading Excel file at row {}: {}", context.readRowHolder().getRowIndex(), exception.getMessage());
        throw exception; 
    }
}
