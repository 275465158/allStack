package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 出库单明细实体
 */
public class PickingDetailInfo extends BaseCompanyAndStatusInfo {

	//出库单id
	private Integer pickingId;
	
	//产品库存id
	private Integer materielId;
	
	//出库数量
	private Float qty;
	
	//计量单位
	private String unit;
	
	//出库单实体
	private PickingInfo pickingList;
	
	//出库库存实体
	private MaterielInventoryInfo materielInventory;
	
	public Integer getPickingId() {
		return pickingId;
	}
	public void setPickingId(Integer pickingId) {
		this.pickingId = pickingId;
	}
	public PickingInfo getPickingList() {
		return pickingList;
	}
	public void setPickingList(PickingInfo pickingList) {
		this.pickingList = pickingList;
	}
	public Integer getMaterielId() {
		return materielId;
	}
	public void setMaterielId(Integer materielId) {
		this.materielId = materielId;
	}
	public Float getQty() {
		return qty;
	}
	public void setQty(Float qty) {
		this.qty = qty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public MaterielInventoryInfo getMaterielInventory() {
		return materielInventory;
	}
	public void setMaterielInventory(MaterielInventoryInfo materielInventory) {
		this.materielInventory = materielInventory;
	}
	
}
