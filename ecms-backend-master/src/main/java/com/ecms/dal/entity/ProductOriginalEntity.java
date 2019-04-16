package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_original")
public class ProductOriginalEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "type")
	private String type;

	@Column(name = "number")
	private String number;

	@Column(name = "product_name_id")
	private Integer productNameId;

	@Column(name = "if_use")
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
