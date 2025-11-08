package com.sim.app.sim_app.features.user.mapper;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sim.app.sim_app.features.user.entity.Permission;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> selectByUserId(@Param("userId") UUID userId);
}
