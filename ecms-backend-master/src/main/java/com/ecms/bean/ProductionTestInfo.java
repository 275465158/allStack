package com.ecms.bean;

public class ProductionTestInfo extends BaseInfo {

    /**主键id**/
    private Integer id;
    /**生产单号**/
    private String productionNumber;
    /**生产类型**/
    private String productionType;
    /**产品名称**/
    private String productName;
    /**生产项次**/
    private String productionBatch;
    /**数量**/
    private Integer totalNumber;
    /**不合格数**/
    private Integer unqualifiedNumber;
    /**公司名称**/
    private Integer companyId;
    /**计划生产日期**/
    private String productionDate;
    /**物料状态1、备齐 2、缺失
     备齐：生产单号所需要的物料都到在仓库
     缺失：生产单号所需要的物料不足**/
    private String materielStatus;
    /**最晚生产日期**/
    private String latestProductionDate;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(String productionNumber) {
        this.productionNumber = productionNumber;
    }

    public String getProductionType() {
        return productionType;
    }

    public void setProductionType(String productionType) {
        this.productionType = productionType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getUnqualifiedNumber() {
        return unqualifiedNumber;
    }

    public void setUnqualifiedNumber(Integer unqualifiedNumber) {
        this.unqualifiedNumber = unqualifiedNumber;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getMaterielStatus() {
        return materielStatus;
    }

    public void setMaterielStatus(String materielStatus) {
        this.materielStatus = materielStatus;
    }

    public String getLatestProductionDate() {
        return latestProductionDate;
    }

    public void setLatestProductionDate(String latestProductionDate) {
        this.latestProductionDate = latestProductionDate;
    }
}
