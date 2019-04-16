/**
 * 
 */
package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author alan.wu
 * 2018年8月26日
 * @Description
 */
@Table(name="materiel_produce_detail")
public class MaterielProduceDetailEntity extends BaseCompanyAndStatusEntity {
	@Column(name="materiel_produce_id")
	private Integer materielProduceId;
	
	@Column(name="materiel_produce_material_id")
	private Integer materialId;
	
	@Column(name="materiel_produce_materiel_pick_qty")
	private Float pickQty;

	public Integer getMaterielProduceId() {
		return materielProduceId;
	}

	public void setMaterielProduceId(Integer materielProduceId) {
		this.materielProduceId = materielProduceId;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Float getPickQty() {
		return pickQty;
	}

	public void setPickQty(Float pickQty) {
		this.pickQty = pickQty;
	}
	
}
