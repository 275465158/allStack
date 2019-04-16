package com.ecms.bean;

import java.util.List;

public class DepartmentIndex {

    private DepartmentInfo departmentInfo;
    // 公司下所有员工
    private List<EmployeeInfo> employeeInfoList;
    // 子部门员工
    private List<EmployeeInfo> childEmployeeInfoList;
    private List<DepartmentInfo> children;
    private boolean isThirdDepartment;

    public DepartmentInfo getDepartmentInfo() {
        return departmentInfo;
    }

    public void setDepartmentInfo(DepartmentInfo departmentInfo) {
        this.departmentInfo = departmentInfo;
    }

    public List<EmployeeInfo> getEmployeeInfoList() {
        return employeeInfoList;
    }

    public void setEmployeeInfoList(List<EmployeeInfo> employeeInfoList) {
        this.employeeInfoList = employeeInfoList;
    }

    public List<EmployeeInfo> getChildEmployeeInfoList() {
        return childEmployeeInfoList;
    }

    public void setChildEmployeeInfoList(List<EmployeeInfo> childEmployeeInfoList) {
        this.childEmployeeInfoList = childEmployeeInfoList;
    }

    public List<DepartmentInfo> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentInfo> children) {
        this.children = children;
    }

    public boolean isThirdDepartment() {
        return isThirdDepartment;
    }

    public void setThirdDepartment(boolean thirdDepartment) {
        isThirdDepartment = thirdDepartment;
    }
}
