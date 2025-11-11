package com.sim.app.sim_app.features.sim.v1.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sim.app.sim_app.features.sim.mapper.SimMapper;
import com.sim.app.sim_app.features.sim.producer.SimImportProducer;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sim.app.sim_app.features.sim.v1.dto.SimMapStruct;
import com.sim.app.sim_app.features.sim.v1.dto.request.CreateSimRequest;
import com.sim.app.sim_app.features.sim.v1.dto.request.SimExcelImportModel;
import com.sim.app.sim_app.features.sim.v1.dto.response.SimExcelExportModel;
import com.sim.app.sim_app.features.sim.v1.dto.response.SimResponse;
import com.sim.app.sim_app.features.sim.v1.service.SimService;
import com.sim.app.sim_app.utils.TaskRunnerUtils;
import com.sim.app.sim_app.utils.ThreadPoolUtil;
import com.sim.app.sim_app.core.dto.PaginationResponse;
import com.sim.app.sim_app.core.exception.ExceptionFactory;
import com.sim.app.sim_app.features.sim.entity.SimEntity;
import com.sim.app.sim_app.features.sim.excel.SimImportExcelListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimServiceImpl implements SimService{
    private final SimMapper simMapper;
    private final SimMapStruct simMapStruct;
    private final SimImportProducer simImportProducer;

    @Transactional
    public SimResponse createSim(CreateSimRequest request) {
        SimEntity exist = simMapper.selectOne(
            new QueryWrapper<SimEntity>().eq("sim_phone_number", request.getSimPhoneNumber())
        );

        if (exist != null) { 
            throw ExceptionFactory.dataAlreadyExists("PhoneNumber " + request.getSimPhoneNumber() + " already exists.");
        }

        SimEntity simEntity = simMapStruct.toEntity(request);
        simEntity.setSimId(UUID.randomUUID());

        simMapper.insert(simEntity);

        return simMapStruct.toResponseDto(simEntity);
    }

    public void importSimsFromExcel(MultipartFile file) throws IOException {
        SimImportExcelListener listener = new SimImportExcelListener(simImportProducer, simMapStruct);

        log.info("Starting to read Excel file: {}", file.getOriginalFilename());

        try {
            EasyExcel.read(
                file.getInputStream(),
                SimExcelImportModel.class,
                listener
            ).sheet().doRead();
            
        } catch (IOException e) {
            log.error("Error reading input stream for Excel file: {}", e.getMessage());
            throw e;
        }
    }

    public SimResponse getSimById(UUID id) {
        SimEntity simEntity = simMapper.selectById(id);

        if (simEntity == null) { 
            log.error("Service:-> getSimById | {}", id);
            throw ExceptionFactory.dataNotFound("Sim ID " + id + " not exists.");
        }

        return simMapStruct.toResponseDto(simEntity);
    }

    public PaginationResponse<SimResponse> getManySim(long page, long size) {
        IPage<SimEntity> pageObject = new Page<>(page, size);

        IPage<SimEntity> entityPage = simMapper.selectPage(pageObject, null);

        List<SimResponse> responseList = entityPage.getRecords().stream()
                    .map(simMapStruct::toResponseDto)
                    .collect(Collectors.toList());

        return PaginationResponse.of(
            responseList,
            entityPage.getTotal(),
            entityPage.getCurrent(),
            entityPage.getSize()
        );
    }

    public List<SimExcelExportModel> getAllSimsForExcelExport() {
        List<SimEntity> allSimEntities = simMapper.selectList(null);

        List<SimExcelExportModel> allSimResponses = allSimEntities.stream()
                    .map(simMapStruct::toExcelModel)
                    .collect(Collectors.toList());

        return allSimResponses;
    }

    public void processExcelDataAnalytics() throws InterruptedException {
        int totalCount = 999999;
        int chunkRows = 5000;
        int chunkLoop = totalCount / chunkRows + (totalCount % chunkRows == 0 ? 0 : 1);
        int ccuPoolRate = 3;
        int threadLoop = chunkLoop / ccuPoolRate + (chunkLoop % ccuPoolRate == 0 ? 0 : 1);

        ThreadPoolTaskExecutor threadPoolTaskExecutor = ThreadPoolUtil.createThreadPoolTaskExecutor(9, 12, threadLoop * ccuPoolRate, 60, "thread-excel-");
        
        try {
            TaskRunnerUtils.runInParallelBatches(totalCount, ccuPoolRate, threadLoop, threadPoolTaskExecutor, 
                (startRows, endRows) -> {
                    System.out.println("Processing: startRows " + startRows + " | endRows " + endRows);
                }
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            threadPoolTaskExecutor.shutdown();
        }
    }
}
