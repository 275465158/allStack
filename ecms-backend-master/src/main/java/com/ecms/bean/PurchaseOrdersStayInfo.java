package com.ecms.bean;

import java.util.List;

/**
 * 
 * @author zhengli
 * 2018年8月1日
 * @Description 待采购订单实体
 */
public class PurchaseOrdersStayInfo extends BaseCompanyAndStatusInfo {

	//待采购编号
	private String purchaseStayCode;

	//待采购数量
	private Float stayPurQty;

	//采购原因
	private String purchaseReason;

	//最晚采购日期
	private String latestPurDate;

	//物料id
	private Integer materialId;

	//物料实体
	private MaterialBomInfo materialBomInfo;

	//订单id
	private Integer orderId;

	//待采购单下单状态 0未下单 1已下单
	private Integer purchaseStayStatus;

	//供应商id
	private Integer supplierId;

	//供应商名称
	private String supplierName;

	private String materialCode;

	private String materialName;

	private String materialType;

	private String materialBomParamValueStr;

	private String originalMaterial;

	private String orderCode;

	private String contractId;

	private List<Integer> ids;

	public String getPurchaseStayCode() { return purchaseStayCode; }

	public void setPurchaseStayCode(String purchaseStayCode) { this.purchaseStayCode = purchaseStayCode; }

	public Float getStayPurQty() {
		return stayPurQty;
	}

	public void setStayPurQty(Float stayPurQty) {
		this.stayPurQty = stayPurQty;
	}

	public String getPurchaseReason() {
		return purchaseReason;
	}

	public void setPurchaseReason(String purchaseReason) {
		this.purchaseReason = purchaseReason;
	}

	public String getLatestPurDate() {
		return latestPurDate;
	}

	public void setLatestPurDate(String latestPurDate) {
		this.latestPurDate = latestPurDate;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public MaterialBomInfo getMaterialBomInfo() {
		return materialBomInfo;
	}

	public void setMaterialBomInfo(MaterialBomInfo materialBomInfo) {
		this.materialBomInfo = materialBomInfo;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getPurchaseStayStatus() {
		return purchaseStayStatus;
	}

	public void setPurchaseStayStatus(Integer purchaseStayStatus) {
		this.purchaseStayStatus = purchaseStayStatus;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getMaterialBomParamValueStr() {
		return materialBomParamValueStr;
	}

	public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
		this.materialBomParamValueStr = materialBomParamValueStr;
	}

	public String getOriginalMaterial() {
		return originalMaterial;
	}

	public void setOriginalMaterial(String originalMaterial) {
		this.originalMaterial = originalMaterial;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
}
