package com.ecms.bean;

public class ProductTypeInfo extends BaseCompanyAndStatusInfo {

	private String type;

	private String number;

	private Integer productNum;

	private Integer ifUse;

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getIfUse() {
		return ifUse;
	}

	public void setIfUse(Integer ifUse) {
		this.ifUse = ifUse;
	}
}
