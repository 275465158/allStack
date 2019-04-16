package com.ecms.bean;

/**
 * @author Richie
 * @version 2018/12/18 10:28
 */
public class HandleEmployeeInfo extends BaseCompanyAndStatusInfo {
    //不合格处理表id
    private Integer handleId;

    //检测表id
    private Integer checkId;

    //人员id
    private Integer employeeId;

    public Integer getHandleId() {
        return handleId;
    }

    public void setHandleId(Integer handleId) {
        this.handleId = handleId;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
}
