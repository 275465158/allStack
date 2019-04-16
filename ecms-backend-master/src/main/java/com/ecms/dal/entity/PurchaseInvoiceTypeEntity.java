package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "purchase_invoice_type")
public class PurchaseInvoiceTypeEntity extends BaseCompanyAndStatusEntity {
	@Column(name = "type_name")
	private String typeName;
	@Column(name = "tax_rate")
	private Integer taxRate;
	@Column(name = "remark")
	private String remark;
	@Column(name = "status")
	private Integer status;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Integer taxRate) {
		this.taxRate = taxRate;
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
