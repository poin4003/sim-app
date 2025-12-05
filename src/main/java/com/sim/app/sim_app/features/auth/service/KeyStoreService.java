package com.sim.app.sim_app.features.auth.service;

import java.util.UUID;

import com.sim.app.sim_app.features.auth.service.schema.result.KeyStoreResult;

public interface KeyStoreService {

    KeyStoreResult getKeyStoreByUserId(UUID userId);

    void deleteKeyStoreByUserId(UUID userId);
}
