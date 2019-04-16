package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhengli
 * 2018年9月3日
 * @Description 工具类型
 */

@Table(name="tool_type")
public class ToolTypeEntity extends BaseCompanyAndStatusEntity {

	//工具类型名称
	@Column(name="tool_type_name")
	private String toolTypeName;

	//工具类型可用状态  1:可用 0:不可用
	@Column(name="status")
	private Integer status;

	public String getToolTypeName() {
		return toolTypeName;
	}

	public void setToolTypeName(String toolTypeName) {
		this.toolTypeName = toolTypeName;
	}

	@Override
	public Integer getStatus() {
		return status;
	}

	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}
}
