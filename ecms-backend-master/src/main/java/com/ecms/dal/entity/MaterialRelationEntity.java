package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name="material_relation")
public class MaterialRelationEntity extends BaseCompanyAndStatusEntity {

	//父物料id
	@Column(name="material_parent_id")
	private Integer materialParentId;

	//子物料id
	@Column(name="material_child_id")
	private Integer materialChildId;

	//所需数量
	@Column(name="quantity")
	private Float quantity;

	public Integer getMaterialParentId() {
		return materialParentId;
	}

	public void setMaterialParentId(Integer materialParentId) {
		this.materialParentId = materialParentId;
	}

	public Integer getMaterialChildId() {
		return materialChildId;
	}

	public void setMaterialChildId(Integer materialChildId) {
		this.materialChildId = materialChildId;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}
}
