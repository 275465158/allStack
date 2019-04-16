package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "repertory")
public class RepertoryEntity extends BaseCompanyAndStatusEntity {

	// 仓库编号
	@Column(name = "repertory_code")
	private String repertoryCode;
	// 仓库名称
	@Column(name = "repertory_name")
	private String repertoryName;
	// 仓库类型
	@Column(name = "repertory_type")
	private String repertoryType;
	// 仓库所属部门
	@Column(name = "repertory_department_id")
	private Integer repertoryDepartmentId;
	// 仓库管理员
	@Column(name = "repertory_manager")
	private String repertoryManager;

	public String getRepertoryCode() {
		return repertoryCode;
	}

	public void setRepertoryCode(String repertoryCode) {
		this.repertoryCode = repertoryCode;
	}

	public String getRepertoryName() {
		return repertoryName;
	}

	public void setRepertoryName(String repertoryName) {
		this.repertoryName = repertoryName;
	}

	public String getRepertoryType() {
		return repertoryType;
	}

	public void setRepertoryType(String repertoryType) {
		this.repertoryType = repertoryType;
	}

	public Integer getRepertoryDepartmentId() {
		return repertoryDepartmentId;
	}

	public void setRepertoryDepartmentId(Integer repertoryDepartmentId) {
		this.repertoryDepartmentId = repertoryDepartmentId;
	}

	public String getRepertoryManager() {
		return repertoryManager;
	}

	public void setRepertoryManager(String repertoryManager) {
		this.repertoryManager = repertoryManager;
	}

}
