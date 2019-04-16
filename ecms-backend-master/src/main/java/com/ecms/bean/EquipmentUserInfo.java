package com.ecms.bean;

import java.util.List;
import java.util.Map;

public class EquipmentUserInfo extends BaseCompanyAndStatusInfo {

    /**主键id**/
    private Integer id;
    /**设备编号**/
    private Integer equipmentNumber;
    /**员工工号**/
    private String employeeId;
    /**员工姓名**/
    private String employeeName;
    /**公司id**/
    private Integer companyId;
    /**部门id**/
    private Integer departmentId;
    /**岗位id**/
    private Integer postId;
    /**设备名称**/
    private String equipmentName;
    /**设备列表**/
    private List<Map<String,Object>> equipmentList;
    private List<Map<String,Object>> tableDataList;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(Integer equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<Map<String, Object>> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Map<String, Object>> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public List<Map<String, Object>> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<Map<String, Object>> tableDataList) {
        this.tableDataList = tableDataList;
    }
}
