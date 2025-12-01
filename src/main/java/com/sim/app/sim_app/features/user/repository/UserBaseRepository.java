package com.sim.app.sim_app.features.user.repository;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sim.app.sim_app.features.user.entity.UserBaseEntity;

@Mapper
public interface UserBaseRepository extends BaseMapper<UserBaseEntity> {

}
