package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/12/18 10:25
 */
@Table(name = "handle_employee")
public class HandleEmployeeEntity extends BaseCompanyAndStatusEntity {
    //不合格处理表id
    @Column(name = "handle_id")
    private Integer handleId;

    //检测表id
    @Column(name = "check_id")
    private Integer checkId;

    //人员id
    @Column(name = "employee_id")
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
