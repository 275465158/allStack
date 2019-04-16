/**
 * 
 */
package com.ecms.bean;


/**
 * @author zhengli
 * 2018年9月3日
 * @Description 工艺工具类型实体
 */
public class ProcessToolTypeInfo extends BaseCompanyAndStatusInfo {

	//工艺工具类型id
	private Integer id;

	//工艺编号
	private Integer processId;

	//工具类型编号
	private Integer toolTypeId;

	//工具类型名称
	private String toolTypeName;

	//是否有型号要求
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

	public Integer getToolTypeId() { return toolTypeId; }

	public void setToolTypeId(Integer toolTypeId) { this.toolTypeId = toolTypeId; }

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
