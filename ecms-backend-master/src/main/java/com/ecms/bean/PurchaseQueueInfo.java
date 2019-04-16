package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 采购清单实体
 */
public class PurchaseQueueInfo extends BaseCompanyAndStatusInfo {
	
	//物料实体
	private MaterialBomInfo materialBom;
	
	//物料id
	private Integer materialId;
	
	//待采购数量
	private Float qty;
	
	//下单数量
	private Float orderQty;
	
	//原因
	private String reason;
	
	//最晚采购时间
	private String lastestTime;
	
	//是否下单  0未下单 1下单
	private Integer ordered;

	public MaterialBomInfo getMaterialBom() {
		return materialBom;
	}

	public void setMaterialBom(MaterialBomInfo materialBom) {
		this.materialBom = materialBom;
		this.materialId = materialBom.getId();
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Float getQty() {
		return qty;
	}

	public void setQty(Float qty) {
		this.qty = qty;
	}

	public Float getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(Float orderQty) {
		this.orderQty = orderQty;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLastestTime() {
		return lastestTime;
	}

	public void setLastestTime(String lastestTime) {
		this.lastestTime = lastestTime;
	}

	public Integer getOrdered() {
		return ordered;
	}

	public void setOrdered(Integer ordered) {
		this.ordered = ordered;
	}
}
