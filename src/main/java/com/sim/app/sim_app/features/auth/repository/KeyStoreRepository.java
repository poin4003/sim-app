package com.sim.app.sim_app.features.auth.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sim.app.sim_app.features.auth.entity.KeyStoreEntity;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KeyStoreRepository extends BaseMapper<KeyStoreEntity> {
    void upsert(KeyStoreEntity entity);
}
