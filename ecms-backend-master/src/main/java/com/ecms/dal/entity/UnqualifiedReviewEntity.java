package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/12/18 9:30
 */
@Table(name="unqualified_review")
public class UnqualifiedReviewEntity extends BaseCompanyAndStatusEntity {
    //采购单id
    @Column(name = "purchase_id")
    private Integer purchaseId;

    //检测单id
    @Column(name = "check_id")
    private Integer checkId;

    //不合格评审单号
    @Column(name = "unqualified_number")
    private String unqualifiedNumber;

    //不合格评审描述
    @Column(name = "unqualified_describe")
    private String unqualifiedDescribe;

    //不合格原因分析
    @Column(name = "unqualified_remark")
    private String unqualifiedRemark;

    //分析时间
    @Column(name = "remark_time")
    private String remarkTime;

    //分析人
    @Column(name = "remark_employee_name")
    private String remarkEmployeeName;

    //不合格处理表id
    @Column(name = "unqualified_handle_id")
    private Integer unqualifiedHandleId;

    //纠正以及处理措施依据
    @Column(name = "handle_basis")
    private String handleBasis;

    //纠正以及处理措施人
    @Column(name = "handle_name")
    private String handleName;

    //纠正以及处理措施时间
    @Column(name = "handle_time")
    private String handleTime;

    //附件url
    @Column(name = "file_url")
    private String fileUrl;

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public String getUnqualifiedNumber() {
        return unqualifiedNumber;
    }

    public void setUnqualifiedNumber(String unqualifiedNumber) {
        this.unqualifiedNumber = unqualifiedNumber;
    }

    public String getUnqualifiedDescribe() {
        return unqualifiedDescribe;
    }

    public void setUnqualifiedDescribe(String unqualifiedDescribe) {
        this.unqualifiedDescribe = unqualifiedDescribe;
    }

    public String getUnqualifiedRemark() {
        return unqualifiedRemark;
    }

    public void setUnqualifiedRemark(String unqualifiedRemark) {
        this.unqualifiedRemark = unqualifiedRemark;
    }

    public String getRemarkTime() {
        return remarkTime;
    }

    public void setRemarkTime(String remarkTime) {
        this.remarkTime = remarkTime;
    }

    public String getRemarkEmployeeName() {
        return remarkEmployeeName;
    }

    public void setRemarkEmployeeName(String remarkEmployeeName) {
        this.remarkEmployeeName = remarkEmployeeName;
    }

    public Integer getUnqualifiedHandleId() {
        return unqualifiedHandleId;
    }

    public void setUnqualifiedHandleId(Integer unqualifiedHandleId) {
        this.unqualifiedHandleId = unqualifiedHandleId;
    }

    public String getHandleBasis() {
        return handleBasis;
    }

    public void setHandleBasis(String handleBasis) {
        this.handleBasis = handleBasis;
    }

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
