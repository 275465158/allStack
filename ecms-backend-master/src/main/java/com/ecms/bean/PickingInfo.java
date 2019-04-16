package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 出库单实体
 */
public class PickingInfo extends BaseCompanyAndStatusInfo {

	//出库单编号
	private String pickingCode;
	
	//生产单号，与生产体系磨合
	private Integer productionId;
	
	//用途
	private String useage;
	
	//出库时间
	private String pickDate;
	
	//制表人
	private String preparedBy;
	
	//领料人
	private String pickedBy;
	
	//领料状态
	private String pickingStatus;

	//物料状态（保存出库时用）
	private Integer materielId;

	//物料状态
	private String materialUnit;

	//出库数量
	private Float totalNum;

	public Integer getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Integer materielId) {
		this.materielId = materielId;
	}

	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	public Float getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Float totalNum) {
		this.totalNum = totalNum;
	}

	public String getPickingCode() {
		return pickingCode;
	}
	public void setPickingCode(String pickingCode) {
		this.pickingCode = pickingCode;
	}
	public Integer getProductionId() {
		return productionId;
	}
	public void setProductionId(Integer productionId) {
		this.productionId = productionId;
	}
	public String getUseage() {
		return useage;
	}
	public void setUseage(String useage) {
		this.useage = useage;
	}
	public String getPickDate() {
		return pickDate;
	}
	public void setPickDate(String pickDate) {
		this.pickDate = pickDate;
	}
	public String getPreparedBy() {
		return preparedBy;
	}
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}
	public String getPickedBy() {
		return pickedBy;
	}
	public void setPickedBy(String pickedBy) {
		this.pickedBy = pickedBy;
	}
	public String getPickingStatus() {
		return pickingStatus;
	}
	public void setPickingStatus(String pickingStatus) {
		this.pickingStatus = pickingStatus;
	}
	
}
