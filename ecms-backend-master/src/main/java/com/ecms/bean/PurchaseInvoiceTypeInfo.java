package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 采购参数-发票类型
 */
public class PurchaseInvoiceTypeInfo extends BaseCompanyAndStatusInfo {

	//发票名称
	private String typeName;
	
	//发票税率
	private Integer taxRate;
	
	//备注
	private String remark;

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

}
