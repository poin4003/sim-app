package com.sim.app.sim_app.features.ops.enums;

import com.sim.app.sim_app.base.BaseEnum;
import com.sim.app.sim_app.utils.EnumUtils;

import lombok.Getter;

@Getter
public enum EndpointTypeEnum implements BaseEnum {
    MQ_BROKER_LISTENER(1, "MQ_BROKER_LISTENER"),
    API_WEBHOOK(2, "API_WEBHOOK"),
    SCHEDULED_JOB(3, "SCHEDULED_JOB"),
    ;

    private final int code;
    private final String description;

    EndpointTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static EndpointTypeEnum fromCode(int code) {
        return EnumUtils.fromCode(EndpointTypeEnum.class, code);
    }    
}
