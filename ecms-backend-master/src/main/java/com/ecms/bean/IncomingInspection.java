package com.ecms.bean;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class IncomingInspection extends BaseInfo {

    /**主键id**/
    private Integer id;
    /**采购单号**/
    private String orderNumber;
    /**供应商名称**/
    private String supplierName;
    /**物料编号**/
    private String materielNumber;
    /**物料名称**/
    private String materielName;
    /**原材料**/
    private String rawMaterial;
    /**参数一**/
    private String parameter1;
    /**参数二**/
    private String parameter2;
    /**参数三**/
    private String parameter3;
    /**参数四**/
    private String parameter4;
    /**参数五**/
    private String parameter5;
    /**总数量**/
    private Integer totalNumber;
    /**不合格数**/
    private Integer totalUnqualifiedNumber;
    /**产品名称**/
    private String productName;
    /**生产类型**/
    private String productionType;
    /**物料状态**/
    private Integer materielStatus;
    /**计划生产时间**/
    private Timestamp productionDate;
    /**公司id**/
    private Integer companyId;
    /**采购原因**/
    private String orderReason;
    /**合格率**/
    private String qualifiedPercent;
    /**合格数**/
    private Integer totalQualifiedNumber;
    /**物料list**/
    private List<Map<String,Object>> materielList;

    public List<Map<String, Object>> getMaterielList() {
        return materielList;
    }

    public void setMaterielList(List<Map<String, Object>> materielList) {
        this.materielList = materielList;
    }

    public String getOrderReason() {
        return orderReason;
    }

    public void setOrderReason(String orderReason) {
        this.orderReason = orderReason;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMaterielNumber() {
        return materielNumber;
    }

    public void setMaterielNumber(String materielNumber) {
        this.materielNumber = materielNumber;
    }

    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }

    public String getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(String rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public String getParameter1() {
        return parameter1;
    }

    public void setParameter1(String parameter1) {
        this.parameter1 = parameter1;
    }

    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2;
    }

    public String getParameter3() {
        return parameter3;
    }

    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3;
    }

    public String getParameter4() {
        return parameter4;
    }

    public void setParameter4(String parameter4) {
        this.parameter4 = parameter4;
    }

    public String getParameter5() {
        return parameter5;
    }

    public void setParameter5(String parameter5) {
        this.parameter5 = parameter5;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductionType() {
        return productionType;
    }

    public void setProductionType(String productionType) {
        this.productionType = productionType;
    }

    public Integer getMaterielStatus() {
        return materielStatus;
    }

    public void setMaterielStatus(Integer materielStatus) {
        this.materielStatus = materielStatus;
    }

    public Timestamp getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Timestamp productionDate) {
        this.productionDate = productionDate;
    }

    public Integer getTotalUnqualifiedNumber() {
        return totalUnqualifiedNumber;
    }

    public void setTotalUnqualifiedNumber(Integer totalUnqualifiedNumber) {
        this.totalUnqualifiedNumber = totalUnqualifiedNumber;
    }

    public String getQualifiedPercent() {
        return qualifiedPercent;
    }

    public void setQualifiedPercent(String qualifiedPercent) {
        this.qualifiedPercent = qualifiedPercent;
    }

    public Integer getTotalQualifiedNumber() {
        return totalQualifiedNumber;
    }

    public void setTotalQualifiedNumber(Integer totalQualifiedNumber) {
        this.totalQualifiedNumber = totalQualifiedNumber;
    }
}
