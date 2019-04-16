package com.ecms.dal.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_drawing")
public class ProductDrawingEntity extends BaseCompanyAndStatusEntity {
    private String name;

    @Column(name = "drawing_remark")
    private String drawingRemark;

    @Column(name = "technology_remark")
    private String technologyRemark;

    private String owner;

    @Column(name = "cad_file")
    private String cadFile;

    @Column(name = "pdf_file")
    private String pdfFile;

    @Column(name = "technology_file")
    private String technologyFile;

    @Column(name = "check_name")
    private String checkName;

    @Column(name = "check_date")
    private Date checkDate;

    @Column(name = "check_result")
    private Integer checkResult;

    @Column(name = "drawing_code")
    private String drawingCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrawingRemark() {
        return drawingRemark;
    }

    public void setDrawingRemark(String drawingRemark) {
        this.drawingRemark = drawingRemark;
    }

    public String getTechnologyRemark() {
        return technologyRemark;
    }

    public void setTechnologyRemark(String technologyRemark) {
        this.technologyRemark = technologyRemark;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCadFile() {
        return cadFile;
    }

    public void setCadFile(String cadFile) {
        this.cadFile = cadFile;
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String getTechnologyFile() {
        return technologyFile;
    }

    public void setTechnologyFile(String technologyFile) {
        this.technologyFile = technologyFile;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public String getDrawingCode() {
        return drawingCode;
    }

    public void setDrawingCode(String drawingCode) {
        this.drawingCode = drawingCode;
    }
}