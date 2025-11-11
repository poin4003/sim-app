package com.sim.app.sim_app.features.sim.v1.dto.request;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class SimExcelImportModel {

    @ExcelProperty(value = "phone_number")
    private String simPhoneNumber;

    @ExcelProperty(value = "selling_price")
    private Integer simSellingPrice;

    @ExcelProperty(value = "dealer_price")
    private Integer simDealerPrice;

    @ExcelProperty(value = "import_price")
    private Integer simImportPrice;
    
    @ExcelProperty(value = "sim_status_string")
    private String simStatusString;
}
