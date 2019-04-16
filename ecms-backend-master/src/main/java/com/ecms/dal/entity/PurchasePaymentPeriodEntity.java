package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "purchase_payment_period")
public class PurchasePaymentPeriodEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "period_name")
	private String periodName;

	@Column(name = "period_days")
	private Integer periodDays;

	@Column(name = "remark")
	private String remark;

	@Column(name = "status")
	private Integer status;

	public Integer getPeriodDays() {
		return periodDays;
	}

	public String getPeriodName() {
		return periodName;
	}

	public String getRemark() {
		return remark;
	}

	@Override
	public Integer getStatus() {
		return status;
	}

	public void setPeriodDays(Integer periodDays) {
		this.periodDays = periodDays;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}

}
