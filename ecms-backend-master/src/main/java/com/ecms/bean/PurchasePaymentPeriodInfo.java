package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 采购参数 - 付款周期
 */
public class PurchasePaymentPeriodInfo extends BaseCompanyAndStatusInfo {

	//周期名称
	private String periodName;
	
	//天数
	private Integer periodDays;
	
	//备注
	private String remark;

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public Integer getPeriodDays() {
		return periodDays;
	}

	public void setPeriodDays(Integer periodDays) {
		this.periodDays = periodDays;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
