package com.sim.app.sim_app.features.auth.service;

public interface RefreshTokenService {
    
    void cleanupExpiredConsumedTokens();
}
