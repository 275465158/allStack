package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "department")
public class DepartmentEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "department_number")
	private Integer departmentNumber;
	@Column(name = "department_name")
	private String departmentName;
	@Column(name = "department_admin_id")
	private Integer departmentAdminId;
	@Column(name = "parent_id")
	private Integer parentId;
	@Column(name = "root_id")
	private Integer rootId;

	public Integer getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(Integer departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getDepartmentAdminId() {
		return departmentAdminId;
	}

	public void setDepartmentAdminId(Integer departmentAdminId) {
		this.departmentAdminId = departmentAdminId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getRootId() {
		return rootId;
	}

	public void setRootId(Integer rootId) {
		this.rootId = rootId;
	}
}
