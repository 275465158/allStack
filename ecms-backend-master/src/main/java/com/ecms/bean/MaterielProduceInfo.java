/**
 * 
 */
package com.ecms.bean;

import java.util.List;

/**
 * @author alan.wu
 * 2018年8月26日
 * @Description 生产发料记录
 */
public class MaterielProduceInfo extends BaseCompanyAndStatusInfo {
	
	//出库单编号
	private String materielProduceNumber;
	//生产计划主键id
	private Integer productPlanId;
	
	//物料状态
	private String materielStatus;

	//生产计划
	private ProductionPlanInfo productionPlan;
	
	//制表人
	private String creator;
	
	//仓库编号
	private Integer repertoryId;
	
	//领料时间
	private String pickedDate;
	
	//领料人
	private String pickedSigner;
	
	//领料状态
	private String pickStatus;
	
	private List<MaterielProduceDetailInfo> detailList;
	
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

	public ProductionPlanInfo getProductionPlan() {
		return productionPlan;
	}

	public void setProductionPlan(ProductionPlanInfo productionPlan) {
		this.productionPlan = productionPlan;
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

	public List<MaterielProduceDetailInfo> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<MaterielProduceDetailInfo> detailList) {
		this.detailList = detailList;
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
