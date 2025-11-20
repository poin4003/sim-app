package com.sim.app.sim_app.features.user.repository;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sim.app.sim_app.features.user.entity.Permission;
import com.sim.app.sim_app.features.user.entity.Role;
import com.sim.app.sim_app.features.user.entity.User;

@Mapper
public interface UserRepository extends BaseMapper<User> {
    List<Role> selectRolesByUserId(@Param("userId") UUID userId);

    List<Permission> selectPermissionByUserId(@Param("userId") UUID userId);
}
