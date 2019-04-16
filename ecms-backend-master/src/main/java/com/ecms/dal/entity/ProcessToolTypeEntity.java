package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhengli
 * 2018年9月3日
 * @Description 工艺工具
 */

@Table(name="process_tool_type")
public class ProcessToolTypeEntity extends BaseCompanyAndStatusEntity {

	//工艺工具类型编号
	@Column(name="id")
	private Integer id;

	//工艺编号
	@Column(name="process_id")
	private Integer processId;

	//工具类型编号
	@Column(name="tool_type_id")
	private Integer toolTypeId;

	//工具类型名称
	@Column(name="tool_type_name")
	private String toolTypeName;

	//是否有型号要求
	@Column(name="default_model_number")
	private Integer defaultModelNumber;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer getToolTypeId() {
		return toolTypeId;
	}

	public void setToolTypeId(Integer toolTypeId) {
		this.toolTypeId = toolTypeId;
	}

	public String getToolTypeName() {
		return toolTypeName;
	}

	public void setToolTypeName(String toolTypeName) {
		this.toolTypeName = toolTypeName;
	}

	public Integer getDefaultModelNumber() {
		return defaultModelNumber;
	}

	public void setDefaultModelNumber(Integer defaultModelNumber) {
		this.defaultModelNumber = defaultModelNumber;
	}
}
