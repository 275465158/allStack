package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhengli
 * 2018年9月3日
 * @Description 工具
 */

@Table(name="tool")
public class ToolEntity extends BaseCompanyAndStatusEntity {

	//工具编号
	@Column(name="id")
	private Integer id;

	//工具类型编号
	@Column(name="tool_type_id")
	private Integer toolTypeId;

	//工具名称
	@Column(name="tool_name")
	private String toolName;

	//工具型号
	@Column(name="tool_model_number")
	private String toolModelNumber;

	//工具最小规格
	@Column(name="tool_min_spec")
	private Integer toolMinSpec;

	//工具最大规格
	@Column(name="tool_max_spec")
	private Integer toolMaxSpec;

	//备注
	@Column(name="remark")
	private String remark;

	//工具可用状态 1:可用 0:不可用
	@Column(name="status")
	private Integer status;

	//工具编号
	@Column(name="tool_code")
	private String toolCode;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getToolTypeId() {
		return toolTypeId;
	}

	public void setToolTypeId(Integer toolTypeId) {
		this.toolTypeId = toolTypeId;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getToolModelNumber() {
		return toolModelNumber;
	}

	public void setToolModelNumber(String toolModelNumber) {
		this.toolModelNumber = toolModelNumber;
	}

	public Integer getToolMinSpec() {
		return toolMinSpec;
	}

	public void setToolMinSpec(Integer toolMinSpec) {
		this.toolMinSpec = toolMinSpec;
	}

	public Integer getToolMaxSpec() {
		return toolMaxSpec;
	}

	public void setToolMaxSpec(Integer toolMaxSpec) {
		this.toolMaxSpec = toolMaxSpec;
	}

	public String getRemark() { return remark; }

	public void setRemark(String remark) { this.remark = remark; }

	@Override
	public Integer getStatus() {
		return status;
	}

	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getToolCode() {
		return toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}
}
