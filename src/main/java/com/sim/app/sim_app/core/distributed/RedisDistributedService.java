package com.sim.app.sim_app.core.distributed;

public interface RedisDistributedService {
    RedisDistributedLocker getDistributedLock(String lockKey); 
}
