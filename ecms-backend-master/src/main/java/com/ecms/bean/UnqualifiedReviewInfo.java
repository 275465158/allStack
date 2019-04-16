package com.ecms.bean;

/**
 * @author Richie
 * @version 2018/12/18 9:46
 */
public class UnqualifiedReviewInfo extends BaseCompanyAndStatusInfo {
    //采购单id
    private Integer purchaseId;

    //检测单id
    private Integer checkId;

    //不合格评审单号
    private String unqualifiedNumber;

    //不合格评审描述
    private String unqualifiedDescribe;

    //不合格原因分析
    private String unqualifiedRemark;

    //分析时间
    private String remarkTime;

    //分析人
    private String remarkEmployeeName;

    //不合格处理表id
    private Integer unqualifiedHandleId;

    //纠正以及处理措施依据
    private String handleBasis;

    //纠正以及处理措施人
    private String handleName;

    //纠正以及处理措施时间
    private String handleTime;

    //附件url
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
