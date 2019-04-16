package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_name")
public class ProductNameEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "type")
	private String type;

	@Column(name = "number")
	private String number;

	@Column(name = "product_type_id")
	private Integer productTypeId;

	@Column(name = "if_use")
	private Integer ifUse;

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
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
