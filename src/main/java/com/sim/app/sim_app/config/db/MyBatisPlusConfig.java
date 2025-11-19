package com.sim.app.sim_app.config.db;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.sim.app.sim_app.features.sims.enums.SimStatusEnum;
import com.sim.app.sim_app.handler.GenericEnumTypeHandler;
import com.sim.app.sim_app.handler.UUIDTypeHandler;

import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.POSTGRE_SQL);
        
        paginationInnerInterceptor.setOptimizeJoin(true); 
        
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.getTypeHandlerRegistry().register(UUIDTypeHandler.class);
            configuration.getTypeHandlerRegistry().register(SimStatusEnum.class, JdbcType.INTEGER, new GenericEnumTypeHandler<>(SimStatusEnum.class));
        };
    }
}
