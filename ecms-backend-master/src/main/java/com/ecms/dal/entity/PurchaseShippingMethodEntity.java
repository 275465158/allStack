package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "purchase_shipping_method")
public class PurchaseShippingMethodEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "method_name")
	private String methodName;

	@Column(name = "remark")
	private String remark;

	@Column(name = "status")
	private Integer status;

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

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
