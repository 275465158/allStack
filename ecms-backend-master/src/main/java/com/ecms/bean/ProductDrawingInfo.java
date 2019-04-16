package com.ecms.bean;

import com.ecms.common.Constants;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductDrawingInfo extends BaseCompanyAndStatusInfo {
    private String name;

    private String drawingRemark;

    private String technologyRemark;

    private String owner;

    private String cadFile;

    private String pdfFile;

    private String technologyFile;

    private String checkName;

    private java.util.Date checkDate;

    private Integer checkResult;

    private MultipartFile cadUploadFile;

    private MultipartFile pdfUploadFile;

    private MultipartFile technologyUploadFile;

    private Date created;

    private String createdDate;

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

    public java.util.Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(java.util.Date checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public MultipartFile getCadUploadFile() {
        return cadUploadFile;
    }

    public void setCadUploadFile(MultipartFile cadUploadFile) {
        this.cadUploadFile = cadUploadFile;
    }

    public MultipartFile getPdfUploadFile() {
        return pdfUploadFile;
    }

    public void setPdfUploadFile(MultipartFile pdfUploadFile) {
        this.pdfUploadFile = pdfUploadFile;
    }

    public MultipartFile getTechnologyUploadFile() {
        return technologyUploadFile;
    }

    public void setTechnologyUploadFile(MultipartFile technologyUploadFile) {
        this.technologyUploadFile = technologyUploadFile;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedDate() {
        if (this.created != null) {
            SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMATTER);
            return format.format(this.created);
        } else {
            return null;
        }
    }

    public String getDrawingCode() {
        return drawingCode;
    }

    public void setDrawingCode(String drawingCode) {
        this.drawingCode = drawingCode;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}