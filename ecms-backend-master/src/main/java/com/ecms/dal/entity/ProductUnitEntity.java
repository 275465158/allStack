package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_unit")
public class ProductUnitEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "type")
	private String type;

	@Column(name = "number")
	private String number;

	@Column(name = "product_original_id")
	private Integer productOriginalId;

	@Column(name = "if_use")
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
