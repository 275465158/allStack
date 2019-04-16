package com.ecms.bean;

/**
 * 
 * @author zhengli
 * 2018/9/3
 * @Description 工具类型实体
 *
 */
public class ToolTypeInfo extends BaseCompanyAndStatusInfo {

	//工具类型编号
	private Integer id;

	//工具类型名称
	private String toolTypeName;

	//型号是否有要求
	private Integer defaultModelNumber;

	//可用状态 1:可用 0:不可用
	private Integer status;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getToolTypeName() {
		return toolTypeName;
	}

	public void setToolTypeName(String toolTypeName) {
		this.toolTypeName = toolTypeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDefaultModelNumber() {
		return defaultModelNumber;
	}

	public void setDefaultModelNumber(Integer defaultModelNumber) {
		this.defaultModelNumber = defaultModelNumber;
	}
}
