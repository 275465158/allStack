/**
 * 
 */
package com.ecms.bean;

/**
 * @author alan.wu
 * 2018年8月26日
 * @Description 发料明细
 */
public class MaterielProduceDetailInfo extends BaseCompanyAndStatusInfo {
	
	private String materielProduceNumber;
	
	private Integer produceId;
	
	private MaterielProduceInfo materielProduceInfo;
	
	private Integer MaterialId;
	
	private MaterialBomModal materialBomModal;
	
	private Float pickQty;

	public String getMaterielProduceNumber() {
		return materielProduceNumber;
	}

	public void setMaterielProduceNumber(String materielProduceNumber) {
		this.materielProduceNumber = materielProduceNumber;
	}

	public Integer getProduceId() {
		return produceId;
	}

	public void setProduceId(Integer produceId) {
		this.produceId = produceId;
	}

	public MaterielProduceInfo getMaterielProduceInfo() {
		return materielProduceInfo;
	}

	public void setMaterielProduceInfo(MaterielProduceInfo materielProduceInfo) {
		this.materielProduceInfo = materielProduceInfo;
	}

	
	public Integer getMaterialId() {
		return MaterialId;
	}

	public void setMaterialId(Integer materialId) {
		MaterialId = materialId;
	}

	public MaterialBomModal getMaterialBomModal() {
		return materialBomModal;
	}

	public void setMaterialBomModal(MaterialBomModal materialBomModal) {
		this.materialBomModal = materialBomModal;
	}

	public Float getPickQty() {
		return pickQty;
	}

	public void setPickQty(Float pickQty) {
		this.pickQty = pickQty;
	}
	
	
}
