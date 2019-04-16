package com.ecms.bean;

/**
 * @author Richie
 * @version 2019/1/3 15:57
 */
public class ProductionProcessLogInfo extends BaseCompanyAndStatusInfo {

    //生产记录表id
    private Integer productionLogId;

    //物料制程id
    private Integer materialProcessId;

    //物料id
    private Integer materialId;

    public Integer getProductionLogId() {
        return productionLogId;
    }

    public void setProductionLogId(Integer productionLogId) {
        this.productionLogId = productionLogId;
    }

    public Integer getMaterialProcessId() {
        return materialProcessId;
    }

    public void setMaterialProcessId(Integer materialProcessId) {
        this.materialProcessId = materialProcessId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }
}
