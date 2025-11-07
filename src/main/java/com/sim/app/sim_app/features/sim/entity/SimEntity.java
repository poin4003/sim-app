package com.sim.app.sim_app.features.sim.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sim.app.sim_app.entity.BaseEntity;

import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(
    name = "sim",
    indexes = {
        @Index(name = "uq_sim_phone_number", columnList = "sim_phone_number", unique = true)
    }
)
@TableName("sim")
public class SimEntity extends BaseEntity {
    @TableId(value = "sim_id", type = IdType.ASSIGN_UUID)
    private String simId;

    @TableField("sim_phone_number")
    private String simPhoneNumber;

    @TableField("sim_status")
    private Integer simStatus;

    @TableField("sim_selling_price")
    private Integer simSellingPrice;

    @TableField("sim_dealer_price")
    private Integer simDealerPrice;

    @TableField("sim_import_price")
    private Integer simImportPrice;
}
