package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月1日
 * @Description 产品属性实体
 */
public class MaterialParameterInfo extends BaseCompanyAndStatusInfo {

	//产品id
	private Integer materialId;
	
	//属性名称
	private String parameterName;
	
	//属性值
	private String parameterValue;
	
	//是否列表可见
	private Boolean isVisible;
	
	//页面上使用缓存变量，实现在表格里是否可被编辑
	private Boolean editFlag;
	
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public Boolean getEditFlag() {
		return false;
	}
	
	public void setEditFlag(Boolean editFlag) {
		
	}
	
}
