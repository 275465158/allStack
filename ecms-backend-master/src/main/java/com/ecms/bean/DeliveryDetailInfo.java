package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月1日
 * @Description 发货明细
 */
public class DeliveryDetailInfo extends BaseCompanyAndStatusInfo {

	//发货单id
	private Integer deliveryId;
	
	//发货单实体
	private DeliveryInfo delivery;
	
	//产品库存id
	private Integer materielId;
	
	//产品库存实体
	private MaterielInventoryInfo materielInventory;
	
	//实际发货数量
	private Float qty;
	
	//单位
	private String unit;

	public Integer getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	public DeliveryInfo getDelivery() {
		return delivery;
	}

	public void setDelivery(DeliveryInfo delivery) {
		this.delivery = delivery;
	}

	public Integer getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Integer materielId) {
		this.materielId = materielId;
	}

	public MaterielInventoryInfo getMaterielInventory() {
		return materielInventory;
	}

	public void setMaterielInventory(MaterielInventoryInfo materielInventory) {
		this.materielInventory = materielInventory;
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
	
	
}
