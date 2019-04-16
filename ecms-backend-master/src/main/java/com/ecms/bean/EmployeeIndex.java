package com.ecms.bean;

import java.util.List;

public class EmployeeIndex {

    private EmployeeInfo employeeInfo;
    private List<DepartmentInfo> firstDepartment;
    private List<DepartmentInfo> secondDepartment;
    private List<DepartmentInfo> thirdDepartment;
    private List<PostInfo> postInfoList;

    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public List<DepartmentInfo> getFirstDepartment() {
        return firstDepartment;
    }

    public void setFirstDepartment(List<DepartmentInfo> firstDepartment) {
        this.firstDepartment = firstDepartment;
    }

    public List<DepartmentInfo> getSecondDepartment() {
        return secondDepartment;
    }

    public void setSecondDepartment(List<DepartmentInfo> secondDepartment) {
        this.secondDepartment = secondDepartment;
    }

    public List<DepartmentInfo> getThirdDepartment() {
        return thirdDepartment;
    }

    public void setThirdDepartment(List<DepartmentInfo> thirdDepartment) {
        this.thirdDepartment = thirdDepartment;
    }

    public List<PostInfo> getPostInfoList() {
        return postInfoList;
    }

    public void setPostInfoList(List<PostInfo> postInfoList) {
        this.postInfoList = postInfoList;
    }
}
