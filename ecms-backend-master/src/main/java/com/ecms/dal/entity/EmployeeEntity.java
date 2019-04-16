package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "employee")
public class EmployeeEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "name")
	private String name;
	private String birthday;
	private String sex;
	private String phone;
	@Column(name = "identification_type")
	private String identificationType;
	@Column(name = "identification_number")
	private String identificationNumber;
	@Column(name = "identification_address")
	private String identificationAddress;
	private String address;
	@Column(name = "root_department_id")
	private Integer rootDepartmentId;
	@Column(name = "department_id")
	private Integer departmentId;
	@Column(name = "post_id")
	private Integer postId;
	@Column(name = "marital_status")
	private String maritalStatus;
	@Column(name = "emergency_contact_name")
	private String emergencyContactName;
	@Column(name = "emergency_contact_phone")
	private String emergencyContactPhone;
	@Column(name = "join_date")
	private String joinDate;
	@Column(name = "dimission_date")
	private String dimissionDate;
	@Column(name = "employee_number")
	private Integer employeeNumber;
	@Column(name = "employee_pic")
	private String employeePic;

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

	public Integer getRootDepartmentId() {
		return rootDepartmentId;
	}

	public void setRootDepartmentId(Integer rootDepartmentId) {
		this.rootDepartmentId = rootDepartmentId;
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

	public Integer getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeePic() {
		return employeePic;
	}

	public void setEmployeePic(String employeePic) {
		this.employeePic = employeePic;
	}
}
