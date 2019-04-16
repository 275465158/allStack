package com.ecms.bean;

public class ProductOriginalInfo extends BaseCompanyAndStatusInfo {

	private String type;

	private String number;

	private Integer productNameId;

	private Integer ifUse;

	public Integer getProductNameId() {
		return productNameId;
	}

	public void setProductNameId(Integer productNameId) {
		this.productNameId = productNameId;
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
