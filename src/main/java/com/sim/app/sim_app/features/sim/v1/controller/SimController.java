package com.sim.app.sim_app.features.sim.v1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sim.app.sim_app.core.controller.BaseController;
import com.sim.app.sim_app.core.dto.PaginationResponse;
import com.sim.app.sim_app.core.vo.ResultMessage;
import com.sim.app.sim_app.features.sim.v1.dto.CreateSimRequest;
import com.sim.app.sim_app.features.sim.v1.dto.SimResponse;
import com.sim.app.sim_app.features.sim.v1.service.SimService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/sim")
@Tag(name = "SIM Management V1", description = "Sim docs")
public class SimController extends BaseController {
    private final SimService simService; 

    @PostMapping("")
    @Operation(summary = "Create a new SIM", description = "Create a new SIM")
    public ResponseEntity<ResultMessage<SimResponse>> createSim(@Valid @RequestBody CreateSimRequest request) {
        SimResponse response = simService.createSim(request);
        return Created(response);
    }

    @GetMapping("")
    @Operation(summary = "Get list of all SIM", description = "Get list of all SIM")
    public ResponseEntity<ResultMessage<PaginationResponse<SimResponse>>> getManySims(
        @RequestParam(defaultValue = "1") long page,
        @RequestParam(defaultValue = "10") long size
    ) {
        PaginationResponse<SimResponse> response = simService.getManySim(page, size);
        return OK("Get many sim success", response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get SIM by ID", description = "Get SIM by ID")
    @RateLimiter(name = "default")
    @CircuitBreaker(name = "checkRandom")
    public ResponseEntity<ResultMessage<SimResponse>> getSimById(@PathVariable String id) {
        log.info("Controller:-> getSimById | {}", id);
        SimResponse response = simService.getSimById(id);
        return OK("Get sim success", response);
    }
}
