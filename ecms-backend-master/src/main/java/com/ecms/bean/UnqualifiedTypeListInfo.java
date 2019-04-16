/**
 * 
 */
package com.ecms.bean;


/**
 * @author zl
 * 2018年8月13日
 * @Description 不合格类型明细记录
 */
public class UnqualifiedTypeListInfo extends BaseCompanyAndStatusInfo {
	
	//检测id
	private Integer checkId;

	//检测单来源：0:来料，1：生产
	private Integer checkSource;

	//不合格类型名称
	private String typeName;

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public Integer getCheckSource() {
		return checkSource;
	}

	public void setCheckSource(Integer checkSource) {
		this.checkSource = checkSource;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
