package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 仓库实体
 */
public class RepertoryInfo extends BaseCompanyAndStatusInfo {

	//仓库编码
	private String repertoryCode;
	
	//仓库名称
	private String repertoryName;
	
	//仓库类型
	private String repertoryType;
	
	//管理部门
	private Integer repertoryDepartmentId;

	//管理部门名称
	private String repertoryDepartmentName;
	
	//管理员
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

	public String getRepertoryDepartmentName() {
		return repertoryDepartmentName;
	}

	public void setRepertoryDepartmentName(String repertoryDepartmentName) {
		this.repertoryDepartmentName = repertoryDepartmentName;
	}
}
