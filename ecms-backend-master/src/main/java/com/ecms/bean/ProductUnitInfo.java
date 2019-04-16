package com.ecms.bean;

public class ProductUnitInfo extends BaseCompanyAndStatusInfo {

	private String type;

	private String number;

	private Integer productOriginalId;

	private Integer ifUse;

	public Integer getProductOriginalId() {
		return productOriginalId;
	}

	public void setProductOriginalId(Integer productOriginalId) {
		this.productOriginalId = productOriginalId;
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
