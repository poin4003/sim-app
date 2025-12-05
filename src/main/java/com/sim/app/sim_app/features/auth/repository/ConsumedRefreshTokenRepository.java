package com.sim.app.sim_app.features.auth.repository;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sim.app.sim_app.features.auth.entity.ConsumedRefreshTokenEntity;

@Mapper
public interface ConsumedRefreshTokenRepository extends BaseMapper<ConsumedRefreshTokenEntity> {
    
}
