package com.ecms.bean;

/**
 * @author Richie
 * @version 2018/11/12 15:20
 */
public class ProductionCheckLogInfo extends BaseCompanyAndStatusInfo {
    //生产单号
    private String productionNumber;

    //数量
    private Float materialNum;

    //合格数量
    private Float qualifiedNum;

    //不合格数量
    private Float unQualifiedNum;

    //合格率
    private String qualifiedPercent;

    //生产记录id
    private Integer productionLogId;

    //物料名称
    private String materialName;

    //物料id
    private Integer materialId;

    //物料编码
    private String materialCode;

    //检测时间
    private String checkDate;

    //检测人id
    private Integer checkPersonId;

    //生产项次
    private String productionBatch;

    //检测单号
    private String checkNumber;

    //检测人姓名
    private String checkPersonName;

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

    private String productPersonName;

    private Integer productPersonId;

    private String productDate;

    private String productBatch;

    private String factoryMaterialCode;

    private String produceDate;

    private String originalMaterial;

    private String materialBomParamValueStr;


    public String getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(String productionNumber) {
        this.productionNumber = productionNumber;
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

    public String getQualifiedPercent() {
        return qualifiedPercent;
    }

    public void setQualifiedPercent(String qualifiedPercent) {
        this.qualifiedPercent = qualifiedPercent;
    }

    public Integer getProductionLogId() {
        return productionLogId;
    }

    public void setProductionLogId(Integer productionLogId) {
        this.productionLogId = productionLogId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getCheckPersonId() {
        return checkPersonId;
    }

    public void setCheckPersonId(Integer checkPersonId) {
        this.checkPersonId = checkPersonId;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getCheckPersonName() {
        return checkPersonName;
    }

    public void setCheckPersonName(String checkPersonName) {
        this.checkPersonName = checkPersonName;
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

    public String getProductPersonName() {
        return productPersonName;
    }

    public void setProductPersonName(String productPersonName) {
        this.productPersonName = productPersonName;
    }

    public Integer getProductPersonId() {
        return productPersonId;
    }

    public void setProductPersonId(Integer productPersonId) {
        this.productPersonId = productPersonId;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public String getFactoryMaterialCode() {
        return factoryMaterialCode;
    }

    public void setFactoryMaterialCode(String factoryMaterialCode) {
        this.factoryMaterialCode = factoryMaterialCode;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
    }

    public String getMaterialBomParamValueStr() {
        return materialBomParamValueStr;
    }

    public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
        this.materialBomParamValueStr = materialBomParamValueStr;
    }
}
