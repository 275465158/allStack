package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "purchase_payment_type")
public class PurchasePaymentTypeEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "payment_name")
	private String paymentName;

	@Column(name = "remark")
	private String remark;

	@Column(name = "status")
	private Integer status;

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
