package com.sim.app.sim_app.features.auth.service.impl;

import java.time.Instant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sim.app.sim_app.features.auth.entity.ConsumedRefreshTokenEntity;
import com.sim.app.sim_app.features.auth.repository.ConsumedRefreshTokenRepository;
import com.sim.app.sim_app.features.auth.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    
    private final ConsumedRefreshTokenRepository consumedRefreshTokenRepository;

    @Scheduled(cron = "0 0 0/6 * * ?")
    @SchedulerLock(
        name = "AuthCleanupExpiredConsumedTokens",
        lockAtLeastFor = "PT5M",
        lockAtMostFor = "PT30M"
    )
    @Transactional
    public void cleanupExpiredConsumedTokens() {
        log.info("Starting cleanup of expired consumed refresh token...");

        Instant now = Instant.now();

        int deleteCount = consumedRefreshTokenRepository.delete(
            new LambdaQueryWrapper<ConsumedRefreshTokenEntity>()
                .lt(ConsumedRefreshTokenEntity::getExpiryDate, now)
        );

        log.info("Finished cleanup. Deleted {} expired consumed token at {}", deleteCount, now);
    }
}
