package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 入库单实体相关类
 */
public class WarehousingEntryMaterialModal extends WarehousingEntryInfo {
	
	//物料实体
	private MaterialBomInfo materialBom;
	
	//供应商
	private SupplierInfo supplier;
	
	//采购订单
	private PurchaseOrderInfo purchaseOrderInfo;
	
	//到货总数
	private Float arrivedTotalQty;
	
	//不合格数量
	private Float unqualifiedTotalQty;
	
	//入库总数
	private Float entryTotalQty;

	private String materialCode;

	private Integer materialId;

	private String materialName;

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	@Override
	public Integer getMaterialId() {
		return materialId;
	}

	@Override
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public MaterialBomInfo getMaterialBom() {
		return materialBom;
	}

	public void setMaterialBom(MaterialBomInfo materialBom) {
		this.materialBom = materialBom;
	}

	public SupplierInfo getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierInfo supplier) {
		this.supplier = supplier;
	}

	public PurchaseOrderInfo getPurchaseOrderInfo() {
		return purchaseOrderInfo;
	}

	public void setPurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo) {
		this.purchaseOrderInfo = purchaseOrderInfo;
	}

	public Float getArrivedTotalQty() {
		return arrivedTotalQty;
	}

	public void setArrivedTotalQty(Float arrivedTotalQty) {
		this.arrivedTotalQty = arrivedTotalQty;
	}

	public Float getUnqualifiedTotalQty() {
		return unqualifiedTotalQty;
	}

	public void setUnqualifiedTotalQty(Float unqualifiedTotalQty) {
		this.unqualifiedTotalQty = unqualifiedTotalQty;
	}

	public Float getEntryTotalQty() {
		return entryTotalQty;
	}

	public void setEntryTotalQty(Float entryTotalQty) {
		this.entryTotalQty = entryTotalQty;
	}
	
	
}
