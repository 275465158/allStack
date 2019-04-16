package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "incoming_unqualified")
public class IncomingUnqualifiedEntity extends BaseCompanyAndStatusEntity {

    //不合格评审单号
    @Column(name="unqualified_review_no")
    private String unqualifiedReviewNo;

    //不合格品描述
    @Column(name="unqualified_describe")
    private String unqualifiedDescribe;

    //附件
    @Column(name="file_url")
    private String fileUrl;

    //不合格原因分析
    @Column(name="unqualified_reason_analysis")
    private String unqualifiedReasonAnalysis;

    //分析人
    @Column(name="analysis_person")
    private String analysisPerson;

    //纠正及处置措施
    @Column(name="disposal_handle_id")
    private Integer disposalHandleId;

    //纠正措施依据
    @Column(name="disposal_causes")
    private String disposalCauses;

    //纠正处置人
    @Column(name="disposal_handler")
    private String disposalHandler;

    //分析时间
    @Column(name="analysed_date")
    private String analysedDate;

    //纠正处置时间
    @Column(name="disposal_date")
    private String disposalDate;

    //采购单明细id
    @Column(name="purchase_orders_id")
    private Integer purchaseOrdersId;

    //物料名称
    @Column(name="material_name")
    private String materialName;

    //供应商名称
    @Column(name="supplier_name")
    private String supplierName;

    //采购原因
    @Column(name="purchase_reason")
    private String purchaseReason;

    //采购单号
    @Column(name="purchase_code")
    private String purchaseCode;

    //物料id
    @Column(name="material_id")
    private Integer materialId;

    //不合格数量
    @Column(name="unqualified_qty")
    private Float unqualifiedQty;

    //检测单id
    @Column(name="check_id")
    private Integer checkId;

    //是否可编辑
    @Column(name="editable")
    private Integer editable;

    //置换物料id
    @Column(name = "changed_material_id")
    protected Integer changedMaterialId;

    //列表编号
    @Column(name="incoming_unqualified_number")
    private Integer incomingUnqualifiedNumber;

    //采购单明细id
    @Column(name="purchase_orders_list_id")
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

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Float getUnqualifiedQty() {
        return unqualifiedQty;
    }

    public void setUnqualifiedQty(Float unqualifiedQty) {
        this.unqualifiedQty = unqualifiedQty;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
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

    public Integer getIncomingUnqualifiedNumber() {
        return incomingUnqualifiedNumber;
    }

    public void setIncomingUnqualifiedNumber(Integer incomingUnqualifiedNumber) {
        this.incomingUnqualifiedNumber = incomingUnqualifiedNumber;
    }

    public Integer getPurchaseOrdersListId() {
        return purchaseOrdersListId;
    }

    public void setPurchaseOrdersListId(Integer purchaseOrdersListId) {
        this.purchaseOrdersListId = purchaseOrdersListId;
    }
}
