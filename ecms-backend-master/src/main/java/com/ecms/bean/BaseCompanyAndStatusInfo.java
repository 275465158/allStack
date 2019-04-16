package com.ecms.bean;

/**
 * 
 * @author linlvping
 * @Description 继承于BaseInfo的二层基类，基于记录需要状态字段以及相关的公司id
 * 自动生成字段对应的getter、setter
 */
public class BaseCompanyAndStatusInfo extends BaseInfo {
	
	//相关公司的id
	protected Integer companyId;
	protected Integer status;

	/**
	 * 返回companyId
	 * @return Integer companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * 返回记录的有效性字段
	 * @return Integer status
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
