package com.sim.app.sim_app.features.sim.v1.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.app.sim_app.features.sim.mapper.SimMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sim.app.sim_app.features.sim.v1.dto.CreateSimRequest;
import com.sim.app.sim_app.features.sim.v1.dto.SimMapStruct;
import com.sim.app.sim_app.features.sim.v1.dto.SimResponse;
import com.sim.app.sim_app.features.sim.v1.service.SimService;
import com.sim.app.sim_app.core.dto.PaginationResponse;
import com.sim.app.sim_app.core.exception.ExceptionFactory;
import com.sim.app.sim_app.features.sim.entity.SimEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimServiceImpl implements SimService{
    private final SimMapper simMapper;
    private final SimMapStruct simMapStruct;

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
}
