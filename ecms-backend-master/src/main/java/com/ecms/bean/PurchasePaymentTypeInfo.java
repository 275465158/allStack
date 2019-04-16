package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 采购参数 - 结算方式
 */
public class PurchasePaymentTypeInfo extends BaseCompanyAndStatusInfo {

	//名称
	private String paymentName;
	
	//备注
	private String remark;

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

}
