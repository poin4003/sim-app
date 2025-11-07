package com.sim.app.sim_app.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.Data;

@Data
public abstract class BaseEntity {
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField("note")
    private String note;

    @TableField("description")
    private String description;
}
