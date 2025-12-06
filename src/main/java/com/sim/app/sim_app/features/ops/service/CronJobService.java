package com.sim.app.sim_app.features.ops.service;

public interface CronJobService {
    
    boolean isJobEnabled(String jobName);

    public void setJobStatus(String jobName, boolean enabled);
}
