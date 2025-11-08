package com.sim.app.sim_app.features.user.entity;

import java.util.List;
import java.util.UUID;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("roles")
public class Role {
    @TableId(value = "role_id", type = IdType.ASSIGN_UUID)
    private UUID roleId;

    @TableField("role_name")
    private String roleName;

    @TableField("role_key")
    private String roleKey;

    @TableField(exist = false)
    private List<Permission> permissions;
}
