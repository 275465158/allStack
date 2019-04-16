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
@Table(name="materiel_produce")
public class MaterielProduceEntity extends BaseCompanyAndStatusEntity {

	@Column(name="materiel_produce_number")
	private String materielProduceNumber;
	
	@Column(name="materiel_produce_product_id")
	private Integer productPlanId;
	
	@Column(name="materiel_produce_materiel_status")
	private String materielStatus;

	@Column(name="materiel_produce_creator")
	private String creator;
	
	@Column(name="materiel_produce_repertory_id")
	private Integer repertoryId;
	
	@Column(name="materiel_produce_picked_date")
	private String pickedDate;
	
	@Column(name="materiel_produce_picked_signer")
	private String pickedSigner;
	
	@Column(name="materiel_produce_pick_status")
	private String pickStatus;

	public String getMaterielProduceNumber() {
		return materielProduceNumber;
	}

	public void setMaterielProduceNumber(String materielProduceNumber) {
		this.materielProduceNumber = materielProduceNumber;
	}

	public Integer getProductPlanId() {
		return productPlanId;
	}

	public void setProductPlanId(Integer productPlanId) {
		this.productPlanId = productPlanId;
	}

	public String getMaterielStatus() {
		return materielStatus;
	}

	public void setMaterielStatus(String materielStatus) {
		this.materielStatus = materielStatus;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public String getPickedDate() {
		return pickedDate;
	}

	public void setPickedDate(String pickedDate) {
		this.pickedDate = pickedDate;
	}

	public String getPickedSigner() {
		return pickedSigner;
	}

	public void setPickedSigner(String pickedSigner) {
		this.pickedSigner = pickedSigner;
	}

	public String getPickStatus() {
		return pickStatus;
	}

	public void setPickStatus(String pickStatus) {
		this.pickStatus = pickStatus;
	}
	
	
}
