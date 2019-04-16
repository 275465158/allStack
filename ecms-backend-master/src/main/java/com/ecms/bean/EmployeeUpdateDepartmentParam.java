package com.ecms.bean;

import java.util.List;

public class EmployeeUpdateDepartmentParam {
    private Integer departmentId;
    private List<Integer> idList;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }
}
