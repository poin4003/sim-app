package com.sim.app.sim_app.features.ops.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sim.app.sim_app.features.ops.entity.ServiceEndpointConfigEntity;

@Mapper
public interface ServiceEndpointConfigMapper extends BaseMapper<ServiceEndpointConfigEntity>{
    
}
