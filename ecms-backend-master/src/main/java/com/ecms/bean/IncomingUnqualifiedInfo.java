package com.ecms.bean;

public class IncomingUnqualifiedInfo extends BaseCompanyAndStatusInfo {

    //不合格评审单号
    private String unqualifiedReviewNo;

    //不合格品描述
    private String unqualifiedDescribe;

    //附件
    private String fileUrl;

    //不合格原因分析
    private String unqualifiedReasonAnalysis;

    //分析人
    private String analysisPerson;

    //纠正及处置措施
    private Integer disposalHandleId;

    //纠正措施依据
    private String disposalCauses;

    //纠正处置人
    private String disposalHandler;

    //分析时间
    private String analysedDate;

    //纠正处置时间
    private String disposalDate;

    //采购单明细id
    private Integer purchaseOrdersId;

    //物料名称
    private String materialName;

    //供应商名称
    private String supplierName;

    //采购原因
    private String purchaseReason;

    //采购单号
    private String purchaseCode;

    //采购人
    private String purchasePerson;

    //物料编号
    private String materialCode;

    //材料
    private String originalMaterial;

    //页面展示的参数值
    private String materialBomParamValueStr;

    //不合格数量
    private Float unqualifiedQty;

    //物料id
    private Integer materialId;

    //检测单id
    private Integer checkId;

    //检测单实体
    private MaterialRepertoryIncomingCheckInfo materialRepertoryIncomingCheckInfo;

    //检测人
    private String checkPerson;

    //是否可编辑
    private Integer editable;

    //置换物料id
    private Integer changedMaterialId;

    //物料批次
    private String materialBatch;

    //页面展示的转换物料bom
    private MaterialBomInfo changeMaterialBomInfo;

    //采购单明细id
    private Integer purchaseOrdersListId;

    public String getUnqualifiedReviewNo() {
        return unqualifiedReviewNo;
    }

    public void setUnqualifiedReviewNo(String unqualifiedReviewNo) {
        this.unqualifiedReviewNo = unqualifiedReviewNo;
    }

    public String getUnqualifiedDescribe() {
        return unqualifiedDescribe;
    }

    public void setUnqualifiedDescribe(String unqualifiedDescribe) {
        this.unqualifiedDescribe = unqualifiedDescribe;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getUnqualifiedReasonAnalysis() {
        return unqualifiedReasonAnalysis;
    }

    public void setUnqualifiedReasonAnalysis(String unqualifiedReasonAnalysis) {
        this.unqualifiedReasonAnalysis = unqualifiedReasonAnalysis;
    }

    public String getAnalysisPerson() {
        return analysisPerson;
    }

    public void setAnalysisPerson(String analysisPerson) {
        this.analysisPerson = analysisPerson;
    }

    public Integer getDisposalHandleId() {
        return disposalHandleId;
    }

    public void setDisposalHandleId(Integer disposalHandleId) {
        this.disposalHandleId = disposalHandleId;
    }

    public String getDisposalCauses() {
        return disposalCauses;
    }

    public void setDisposalCauses(String disposalCauses) {
        this.disposalCauses = disposalCauses;
    }

    public String getDisposalHandler() {
        return disposalHandler;
    }

    public void setDisposalHandler(String disposalHandler) {
        this.disposalHandler = disposalHandler;
    }

    public String getAnalysedDate() {
        return analysedDate;
    }

    public void setAnalysedDate(String analysedDate) {
        this.analysedDate = analysedDate;
    }

    public String getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(String disposalDate) {
        this.disposalDate = disposalDate;
    }

    public Integer getPurchaseOrdersId() {
        return purchaseOrdersId;
    }

    public void setPurchaseOrdersId(Integer purchaseOrdersId) {
        this.purchaseOrdersId = purchaseOrdersId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPurchaseReason() {
        return purchaseReason;
    }

    public void setPurchaseReason(String purchaseReason) {
        this.purchaseReason = purchaseReason;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getPurchasePerson() {
        return purchasePerson;
    }

    public void setPurchasePerson(String purchasePerson) {
        this.purchasePerson = purchasePerson;
    }

    public Float getUnqualifiedQty() {
        return unqualifiedQty;
    }

    public void setUnqualifiedQty(Float unqualifiedQty) {
        this.unqualifiedQty = unqualifiedQty;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
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

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public MaterialRepertoryIncomingCheckInfo getMaterialRepertoryIncomingCheckInfo() {
        return materialRepertoryIncomingCheckInfo;
    }

    public void setMaterialRepertoryIncomingCheckInfo(MaterialRepertoryIncomingCheckInfo materialRepertoryIncomingCheckInfo) {
        this.materialRepertoryIncomingCheckInfo = materialRepertoryIncomingCheckInfo;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public Integer getEditable() {
        return editable;
    }

    public void setEditable(Integer editable) {
        this.editable = editable;
    }

    public Integer getChangedMaterialId() {
        return changedMaterialId;
    }

    public void setChangedMaterialId(Integer changedMaterialId) {
        this.changedMaterialId = changedMaterialId;
    }

    public String getMaterialBatch() {
        return materialBatch;
    }

    public void setMaterialBatch(String materialBatch) {
        this.materialBatch = materialBatch;
    }

    public MaterialBomInfo getChangeMaterialBomInfo() {
        return changeMaterialBomInfo;
    }

    public void setChangeMaterialBomInfo(MaterialBomInfo changeMaterialBomInfo) {
        this.changeMaterialBomInfo = changeMaterialBomInfo;
    }

    public Integer getPurchaseOrdersListId() {
        return purchaseOrdersListId;
    }

    public void setPurchaseOrdersListId(Integer purchaseOrdersListId) {
        this.purchaseOrdersListId = purchaseOrdersListId;
    }
}
