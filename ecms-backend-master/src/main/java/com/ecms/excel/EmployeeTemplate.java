package com.ecms.excel;

import com.ecms.annotation.ExcelField;
import com.ecms.annotation.ExcelSheet;
import org.apache.poi.hssf.util.HSSFColor;

import javax.validation.constraints.NotNull;

@ExcelSheet(name = "员工列表", headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
public class EmployeeTemplate extends BaseTemplate {

    @NotNull
    @ExcelField(name = "员工姓名(必填)")
    private String name;
    @NotNull
    @ExcelField(name = "出生日期（2000-08-08）")
    private String birthday;
    @NotNull
    @ExcelField(name = "性别")
    private String sex;
    @NotNull
    @ExcelField(name = "联系电话")
    private String phone;
    @NotNull
    @ExcelField(name = "证件类型(必填)")
    private String identificationType;
    @NotNull
    @ExcelField(name = "证件号码(必填)")
    private String identificationNumber;
    @NotNull
    @ExcelField(name = "证件住址")
    private String identificationAddress;
    @NotNull
    @ExcelField(name = "现居住地址")
    private String address;
    @NotNull
    @ExcelField(name = "部门(必填)")
    private String departmentName;
//    @ExcelField(name = "一级部门")
//    private String firstDepartmentName;
//    @ExcelField(name = "二级部门")
//    private String secondDepartmentName;
//    @ExcelField(name = "三级部门")
//    private String thirdDepartmentName;
    @NotNull
    @ExcelField(name = "岗位(必填)")
    private String postName;
    @NotNull
    @ExcelField(name = "婚姻状况")
    private String maritalStatus;
    @ExcelField(name = "紧急联系人")
    private String emergencyContactName;
    @ExcelField(name = "紧急联系电话")
    private String emergencyContactPhone;
    @ExcelField(name = "入职日期（2000-08-08）")
    private String joinDate;
    @ExcelField(name = "离职日期（2000-08-08）")
    private String dimissionDate;
    @ExcelField(name = "状态（在职/离职）")
    private String statusDisplay;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getIdentificationAddress() {
        return identificationAddress;
    }

    public void setIdentificationAddress(String identificationAddress) {
        this.identificationAddress = identificationAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public String getFirstDepartmentName() {
//        return firstDepartmentName;
//    }
//
//    public void setFirstDepartmentName(String firstDepartmentName) {
//        this.firstDepartmentName = firstDepartmentName;
//    }
//
//    public String getSecondDepartmentName() {
//        return secondDepartmentName;
//    }
//
//    public void setSecondDepartmentName(String secondDepartmentName) {
//        this.secondDepartmentName = secondDepartmentName;
//    }
//
//    public String getThirdDepartmentName() {
//        return thirdDepartmentName;
//    }
//
//    public void setThirdDepartmentName(String thirdDepartmentName) {
//        this.thirdDepartmentName = thirdDepartmentName;
//    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getDimissionDate() {
        return dimissionDate;
    }

    public void setDimissionDate(String dimissionDate) {
        this.dimissionDate = dimissionDate;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public void setStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
    }
}
