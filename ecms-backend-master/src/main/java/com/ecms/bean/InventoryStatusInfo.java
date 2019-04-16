package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月1日
 * @Description 库存状态
 * @deprecated 暂时无用
 */
public class InventoryStatusInfo extends BaseCompanyAndStatusInfo {
	private String statusName;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
