package com.sim.app.sim_app.features.ops.entity;

import java.util.UUID;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sim.app.sim_app.base.BaseEntity;
import com.sim.app.sim_app.features.ops.enums.EndpointStatusEnum;
import com.sim.app.sim_app.features.ops.enums.EndpointTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("service_endpoint_configs")
public class ServiceEndpointConfigEntity extends BaseEntity {
    @TableId(value="service_endpoint_config_id", type=IdType.ASSIGN_UUID)
    private UUID serviceEndpointConfigId;

    @TableField("endpoint_type")
    private EndpointTypeEnum endpointType;

    @TableField("endpoint_status")
    private EndpointStatusEnum endpointStatus;

    @TableField("topic")
    private String topic;

    @TableField("consumer_group")
    private String consumerGroup;

    @TableField("concurrency")
    private Integer concurrency;

    @TableField("filter_key")
    private String filterKey;

    @TableField("filter_value")
    private String filterValue;
}
