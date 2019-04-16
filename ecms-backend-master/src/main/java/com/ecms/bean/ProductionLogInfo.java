package com.ecms.bean;

import java.util.List;
import java.util.Map;

public class ProductionLogInfo extends BaseCompanyAndStatusInfo {
    /**生产单号**/
    private String productionNumber;
    /**生产类型**/
    private String productionType;
    /**产品名称**/
    private String materialName;
    /**生产项次**/
    private String productBatch;
    /**计划生产时间**/
    private String planProductDate;
    /**生产时间**/
    private String productTime;
    /**最晚生产时间**/
    private String latestProductDate;
    /**数量**/
    private Float materialNum;
    /**合格数**/
    private Float qualifiedNum;
    /**不合格数**/
    private Float unQualifiedNum;
    /**物料id**/
    private Integer materialId;
    /**原图材料**/
    private String originalMaterial;
    /**单位**/
    private String materialUnit;
    /**检测状态0：待检测，1：已检测**/
    private Integer checkStatus;
    /**物料编码**/
    private String materialCode;
    /**合格率**/
    private String qualifiedPercent;
    /**生产计划id**/
    private Integer productionPlanId;
    /**工厂编号**/
    private String factoryMaterialCode;
    /**订单号**/
    private String orderCode;
    /**合同编号**/
    private String contractId;
    /**制程id**/
    private Integer materialProcessId;
    /**完成数量**/
    private Float completeQty;
    /**已完成数量**/
    private Float completedQty;

    //检测比例
    private String checkPercent;

    //检验数量
    private Float checkNum;

    //不合格类型
    private String unQualifiedType;

    //不合格描述
    private String unQualifiedRemark;

    //附件url
    private String fileUrl;

    //checkResult
    private String checkResult;

    //检测合格率
    private String checkQualifiedPercent;

    //检测记录list
    private List<Map<String,String>> resultList;
    //不合格类型
    private List<String> unqualifiedTypeFormList;

    /**制程id**/
    private Integer processId;

    /**制程名称**/
    private String processName;

    /**根据生产制程完成的数量**/
    private Float processProductQty;

    //检测记录list
    private List<String> temp;

    /**制程检测Flag,0：不检测，1：检测**/
    private Integer checkFlag;

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

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public String getPlanProductDate() {
        return planProductDate;
    }

    public void setPlanProductDate(String planProductDate) {
        this.planProductDate = planProductDate;
    }

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public String getLatestProductDate() {
        return latestProductDate;
    }

    public void setLatestProductDate(String latestProductDate) {
        this.latestProductDate = latestProductDate;
    }

    public Float getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(Float materialNum) {
        this.materialNum = materialNum;
    }

    public Float getQualifiedNum() {
        return qualifiedNum;
    }

    public void setQualifiedNum(Float qualifiedNum) {
        this.qualifiedNum = qualifiedNum;
    }

    public Float getUnQualifiedNum() {
        return unQualifiedNum;
    }

    public void setUnQualifiedNum(Float unQualifiedNum) {
        this.unQualifiedNum = unQualifiedNum;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getQualifiedPercent() {
        return qualifiedPercent;
    }

    public void setQualifiedPercent(String qualifiedPercent) {
        this.qualifiedPercent = qualifiedPercent;
    }

    public Integer getProductionPlanId() {
        return productionPlanId;
    }

    public void setProductionPlanId(Integer productionPlanId) {
        this.productionPlanId = productionPlanId;
    }

    public String getFactoryMaterialCode() {
        return factoryMaterialCode;
    }

    public void setFactoryMaterialCode(String factoryMaterialCode) {
        this.factoryMaterialCode = factoryMaterialCode;
    }

    public String getCheckPercent() {
        return checkPercent;
    }

    public void setCheckPercent(String checkPercent) {
        this.checkPercent = checkPercent;
    }

    public Float getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Float checkNum) {
        this.checkNum = checkNum;
    }

    public String getUnQualifiedType() {
        return unQualifiedType;
    }

    public void setUnQualifiedType(String unQualifiedType) {
        this.unQualifiedType = unQualifiedType;
    }

    public String getUnQualifiedRemark() {
        return unQualifiedRemark;
    }

    public void setUnQualifiedRemark(String unQualifiedRemark) {
        this.unQualifiedRemark = unQualifiedRemark;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckQualifiedPercent() {
        return checkQualifiedPercent;
    }

    public void setCheckQualifiedPercent(String checkQualifiedPercent) {
        this.checkQualifiedPercent = checkQualifiedPercent;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getMaterialProcessId() {
        return materialProcessId;
    }

    public void setMaterialProcessId(Integer materialProcessId) {
        this.materialProcessId = materialProcessId;
    }

    public Float getCompleteQty() {
        return completeQty;
    }

    public void setCompleteQty(Float completeQty) {
        this.completeQty = completeQty;
    }

    public Float getCompletedQty() {
        return completedQty;
    }

    public void setCompletedQty(Float completedQty) {
        this.completedQty = completedQty;
    }

    public List<Map<String, String>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, String>> resultList) {
        this.resultList = resultList;
    }

    public List<String> getUnqualifiedTypeFormList() {
        return unqualifiedTypeFormList;
    }

    public void setUnqualifiedTypeFormList(List<String> unqualifiedTypeFormList) {
        this.unqualifiedTypeFormList = unqualifiedTypeFormList;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Float getProcessProductQty() {
        return processProductQty;
    }

    public void setProcessProductQty(Float processProductQty) {
        this.processProductQty = processProductQty;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public List<String> getTemp() {
        return temp;
    }

    public void setTemp(List<String> temp) {
        this.temp = temp;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }
}
