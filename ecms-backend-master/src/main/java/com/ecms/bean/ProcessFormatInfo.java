/**
 * 
 */
package com.ecms.bean;


/**
 * @author zhengli
 * 2018年9月3日
 * @Description 工艺格式实体
 */
public class ProcessFormatInfo extends BaseCompanyAndStatusInfo {

	//工艺格式编号
	private Integer id;

	//工艺编号
	private Integer processId;

	//工艺格式名称
	private String processFormatName;

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

	public String getProcessFormatName() {
		return processFormatName;
	}

	public void setProcessFormatName(String processFormatName) {
		this.processFormatName = processFormatName;
	}
}
