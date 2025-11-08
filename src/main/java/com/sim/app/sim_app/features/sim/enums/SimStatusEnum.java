package com.sim.app.sim_app.features.sim.enums;

import com.sim.app.sim_app.base.BaseEnum;

public enum SimStatusEnum implements BaseEnum {
    ACTIVE(1, "Active"),
    INACTIVE(2, "Inactive"),
    BLOCKED(3, "Blocked"),
    PICKED(4, "Picked"),
    DELETED(5, "Deleted");

    private final int code;
    private final String description;

    SimStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static SimStatusEnum fromCode(int code) {
        for (SimStatusEnum s : values()) {
            if (s.getCode() == code) return s;
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    } 
}
