package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="purchase_orders_stay")
public class PurchaseOrdersStayEntity extends BaseCompanyAndStatusEntity {

	//待采购编号
	@Column(name="purchase_stay_code")
	private String purchaseStayCode;

	//待采购数量
	@Column(name="stay_pur_qty")
	private Float stayPurQty;

	//采购原因
	@Column(name="purchase_reason")
	private String purchaseReason;

	//最晚采购日期
	@Column(name="latest_pur_date")
	private String latestPurDate;

	//物料id
	@Column(name="material_id")
	private Integer materialId;

	//订单id
	@Column(name="order_id")
	private Integer orderId;

	//待采购单下单状态 0未下单 1已下单
	@Column(name="purchase_stay_status")
	private Integer purchaseStayStatus;

	//供应商id
	@Column(name="supplier_id")
	private Integer supplierId;

	//供应商名称
	@Column(name="supplier_name")
	private String supplierName;

	@Column(name="material_code")
	private String materialCode;

	@Column(name="material_name")
	private String materialName;

	@Column(name="material_type")
	private String materialType;

	@Column(name="material_bom_param_value_str")
	private String materialBomParamValueStr;

	@Column(name="original_material")
	private String originalMaterial;

	@Column(name="purchase_stay_number")
	private Integer purchaseStayNumber;

	@Column(name="seller_order_product_id")
	private Integer sellerOrderProductId;

	@Column(name="order_need_qty")
	private Float orderNeedQty;

	public String getPurchaseStayCode() {
		return purchaseStayCode;
	}

	public void setPurchaseStayCode(String purchaseStayCode) {
		this.purchaseStayCode = purchaseStayCode;
	}

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

	public Integer getPurchaseStayNumber() {
		return purchaseStayNumber;
	}

	public void setPurchaseStayNumber(Integer purchaseStayNumber) {
		this.purchaseStayNumber = purchaseStayNumber;
	}

	public Integer getSellerOrderProductId() {
		return sellerOrderProductId;
	}

	public void setSellerOrderProductId(Integer sellerOrderProductId) {
		this.sellerOrderProductId = sellerOrderProductId;
	}

	public Float getOrderNeedQty() {
		return orderNeedQty;
	}

	public void setOrderNeedQty(Float orderNeedQty) {
		this.orderNeedQty = orderNeedQty;
	}
}
