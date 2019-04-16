package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "incoming_inspection")
public class IncomingInspectionEntity extends AbstractEntity {

    private Integer id;
    @Column(name="order_number")
    private String orderNumber;
    @Column(name="supplier_name")
    private String supplierName;
    @Column(name="materiel_number")
    private String materielNumber;
    @Column(name="materiel_name")
    private String materielName;
    @Column(name="raw_material")
    private String rawMaterial;
    private String parameter1;
    private String parameter2;
    private String parameter3;
    private String parameter4;
    private String parameter5;
    @Column(name="total_number")
    private Integer totalNumber;
    @Column(name="total_unqualified_number")
    private Integer totalUnqualifiedNumber;
    @Column(name="product_name")
    private String productName;
    @Column(name="production_type")
    private String productionType;
    @Column(name="materiel_status")
    private Integer materielStatus;
    @Column(name="production_date")
    private Timestamp productionDate;
    @Column(name="company_id")
    private Integer companyId;
    @Column(name = "order_reason")
    private String orderReason;
    @Column(name = "qualified_percent")
    private String qualifiedPercent;
    @Column(name = "total_qualified_number")
    private String totalQualifiedNumber;

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

    public String getTotalQualifiedNumber() {
        return totalQualifiedNumber;
    }

    public void setTotalQualifiedNumber(String totalQualifiedNumber) {
        this.totalQualifiedNumber = totalQualifiedNumber;
    }
}
