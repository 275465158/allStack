package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "product_unqualified_report")
public class UnqualifiedProductEntity extends BaseCompanyAndStatusEntity {

    //不合格单号
    @Column(name = "product_unqualified_report_order_number")
    protected String orderNo;

    //合同编号
    @Column(name = "product_unqualified_report_purchase_order")
    protected String purchaseOrderNo;

    //生产单号
    @Column(name = "product_unqualified_report_production_number")
    protected String productionNo;

    //生产项号
    @Column(name = "product_unqualified_report_production_item_number")
    protected String productionItemNo;

    //物料编码
    @Column(name = "product_unqualified_report_material_number")
    protected String materialNumber;

    //物料名称
    @Column(name = "product_unqualified_report_material_name")
    protected String materialName;

    //材料
    @Column(name = "product_unqualified_report_origin_material")
    protected String originMaterial;

    //参数
    @Column(name = "product_unqualified_report_parameters")
    protected String parameters;

    //生产者（负责人）
    @Column(name = "product_unqualified_report_production_responsible")
    protected String productionResponsible;

    //生产日期
    @Column(name = "product_unqualified_report_production_date")
    protected String productionDate;

    //数量
    @Column(name = "product_unqualified_report_qty")
    protected Float qty;

    //不合格数量
    @Column(name = "product_unqualified_report_unqualified_qty")
    protected Float unqualifiedQty;

    //检验员
    @Column(name = "product_unqualified_report_inspector")
    protected String inspector;

    //检验时间
    @Column(name = "product_unqualified_report_inspect_date")
    protected String inspectDate;

    //不合格类型
    @Column(name = "product_unqualified_report_unqualified_type")
    protected String unqualifiedType;

    //不合格品描述
    @Column(name = "product_unqualified_report_unqualified_desc")
    protected String unqualifiedDescription;

    //相关附件
    @Column(name = "product_unqualified_report_attach")
    protected String attachmentFiles;

    //责任人
    @Column(name = "product_unqualified_report_person_responsible")
    protected String personResponsible;

    //责任部门
    @Column(name = "product_unqualified_report_depart_responsible")
    protected String departResponsible;

    //责任评定人
    @Column(name = "product_unqualified_report_person_review_responsible")
    protected String personReviewResponsible;

    //责任评定时间
    @Column(name = "product_unqualified_report_review_date")
    protected String reviewDate;

    //不合格原因分析
    @Column(name = "product_unqualified_report_analysis_causes")
    protected String analysisCauses;

    //分析人
    @Column(name = "product_unqualified_report_analyser")
    protected String analyser;

    //分析时间
    @Column(name = "product_unqualified_report_analysed_date")
    protected String analysedDate;

    //纠正处置措施
    @Column(name = "product_unqualified_report_disposal_handle_id")
    protected Integer disposalHandleId;

    //纠正措施依据
    @Column(name = "product_unqualified_report_disposal_causes")
    protected String disposalCauses;

    //纠正处置人
    @Column(name = "product_unqualified_report_disposal_handler")
    protected String disposalHandler;

    //纠正处置时间
    @Column(name = "product_unqualified_report_disposal_date")
    protected String disposalDate;

    //不合格记录id
    @Column(name = "product_unqualified_report_check_log_id")
    protected Integer checkLogId;

    //记录可编辑
    @Column(name = "product_unqualified_report_editable")
    protected Integer editable;

    //置换物料id
    @Column(name = "product_unqualified_report_changed_material_id")
    protected Integer changedMaterialId;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getProductionNo() {
        return productionNo;
    }

    public void setProductionNo(String productionNo) {
        this.productionNo = productionNo;
    }

    public String getProductionItemNo() {
        return productionItemNo;
    }

    public void setProductionItemNo(String productionItemNo) {
        this.productionItemNo = productionItemNo;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getOriginMaterial() {
        return originMaterial;
    }

    public void setOriginMaterial(String originMaterial) {
        this.originMaterial = originMaterial;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getProductionResponsible() {
        return productionResponsible;
    }

    public void setProductionResponsible(String productionResponsible) {
        this.productionResponsible = productionResponsible;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public Float getUnqualifiedQty() {
        return unqualifiedQty;
    }

    public void setUnqualifiedQty(Float unqualifiedQty) {
        this.unqualifiedQty = unqualifiedQty;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getInspectDate() {
        return inspectDate;
    }

    public void setInspectDate(String inspectDate) {
        this.inspectDate = inspectDate;
    }

    public String getUnqualifiedType() {
        return unqualifiedType;
    }

    public void setUnqualifiedType(String unqualifiedType) {
        this.unqualifiedType = unqualifiedType;
    }

    public String getUnqualifiedDescription() {
        return unqualifiedDescription;
    }

    public void setUnqualifiedDescription(String unqualifiedDescription) {
        this.unqualifiedDescription = unqualifiedDescription;
    }

    public String getAttachmentFiles() {
        return attachmentFiles;
    }

    public void setAttachmentFiles(String attachmentFiles) {
        this.attachmentFiles = attachmentFiles;
    }

    public String getPersonResponsible() {
        return personResponsible;
    }

    public void setPersonResponsible(String personResponsible) {
        this.personResponsible = personResponsible;
    }

    public String getDepartResponsible() {
        return departResponsible;
    }

    public void setDepartResponsible(String departResponsible) {
        this.departResponsible = departResponsible;
    }

    public String getPersonReviewResponsible() {
        return personReviewResponsible;
    }

    public void setPersonReviewResponsible(String personReviewResponsible) {
        this.personReviewResponsible = personReviewResponsible;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getAnalysisCauses() {
        return analysisCauses;
    }

    public void setAnalysisCauses(String analysisCauses) {
        this.analysisCauses = analysisCauses;
    }

    public String getAnalyser() {
        return analyser;
    }

    public void setAnalyser(String analyser) {
        this.analyser = analyser;
    }

    public String getAnalysedDate() {
        return analysedDate;
    }

    public void setAnalysedDate(String analysedDate) {
        this.analysedDate = analysedDate;
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

    public String getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(String disposalDate) {
        this.disposalDate = disposalDate;
    }

    public Integer getCheckLogId() {
        return checkLogId;
    }

    public void setCheckLogId(Integer checkLogId) {
        this.checkLogId = checkLogId;
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
}
