package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 采购参数 - 运输方式
 */
public class PurchaseShippingMethodInfo extends BaseCompanyAndStatusInfo {

	//名称
	private String methodName;
	
	//备注
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
