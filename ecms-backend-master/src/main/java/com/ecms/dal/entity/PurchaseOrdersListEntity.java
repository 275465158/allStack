package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name="purchase_orders_list")
public class PurchaseOrdersListEntity extends BaseCompanyAndStatusEntity {

	//物料id
	@Column(name="material_id")
	private Integer materialId;

	//采购单id
	@Column(name="purchase_orders_id")
	private Integer purchaseOrdersId;

	//物料编码
	@Column(name="material_code")
	private String materialCode;

	//物料名称
	@Column(name="material_name")
	private String materialName;

	//已收货数量
	@Column(name="received_qty")
	private Float receivedQty;

	//未收货数量
	@Column(name="un_received_qty")
	private Float unReceivedQty;

	//待收货数量（实际采购数量）
	@Column(name="stay_pur_qty")
	private Float stayPurQty;

	//不合格数
	@Column(name="unqualified_qty")
	private Float unqualifiedQty;

	//采购单价（下单实际价格）
	@Column(name="purchase_price")
	private BigDecimal purchasePrice;

	//明细总价
	@Column(name="purchase_price_total")
	private BigDecimal purchasePriceTotal;

	//采购数量
	@Column(name="purchase_qty")
	private Float purchaseQty;

	//结算方式
	@Column(name="payment_method")
	private Integer paymentMethod;

	//仓库id
	@Column(name="repertory_id")
	private Integer repertoryId;

	//预计到货时间
	@Column(name="arrive_date")
	private String arriveDate;

	//核销人
	@Column(name="verificat_person")
	private String verificatPerson;

	//核销时间
	@Column(name="verificat_date")
	private String verificatDate;

	//核销状态
	@Column(name="verificat_status")
	private Integer verificatStatus;

	//结算数量
	@Column(name="settlement_qty")
	private Float settlementQty;

	//供应商id
	@Column(name="supplier_id")
	private Integer supplierId;

	//收货状态 0:未收货 1:部分收货 2:已收货
	@Column(name="received_status")
	private Integer receivedStatus;

	//实际收货数
	@Column(name="actual_delivery_qty")
	private Float actualDeliveryQty;

	//采购原因
	@Column(name="purchase_reason")
	private String purchaseReason;

	/**工厂物料编码**/
	@Column(name="factory_material_code")
	private String factoryMaterialCode;

	/**物料类型**/
	@Column(name="number")
	private String number;

	/**原图材料**/
	@Column(name="original_material")
	private String originalMaterial;

	/**图号**/
	@Column(name="drawing_code")
	private String drawingCode;

	/**参数**/
	@Column(name="material_bom_param_value_str")
	private String materialBomParamValueStr;

	/**物料来源**/
	@Column(name="material_source")
	private String materialSource;

	//采购单号
	@Column(name="purchase_code")
	private String purchaseCode;

	//物料类型
	@Column(name="type")
	private String type;

	//供应商名称
	@Column(name="supplier_name")
	private String supplierName;

	@Column(name="review_status")
	private Integer reviewStatus;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getPurchaseOrdersId() {
		return purchaseOrdersId;
	}

	public void setPurchaseOrdersId(Integer purchaseOrdersId) {
		this.purchaseOrdersId = purchaseOrdersId;
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

	public Float getReceivedQty() {
		return receivedQty;
	}

	public void setReceivedQty(Float receivedQty) {
		this.receivedQty = receivedQty;
	}

	public Float getUnReceivedQty() {
		return unReceivedQty;
	}

	public void setUnReceivedQty(Float unReceivedQty) {
		this.unReceivedQty = unReceivedQty;
	}

	public Float getStayPurQty() {
		return stayPurQty;
	}

	public void setStayPurQty(Float stayPurQty) {
		this.stayPurQty = stayPurQty;
	}

	public Float getUnqualifiedQty() {
		return unqualifiedQty;
	}

	public void setUnqualifiedQty(Float unqualifiedQty) {
		this.unqualifiedQty = unqualifiedQty;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getPurchasePriceTotal() {
		return purchasePriceTotal;
	}

	public void setPurchasePriceTotal(BigDecimal purchasePriceTotal) {
		this.purchasePriceTotal = purchasePriceTotal;
	}

	public Float getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(Float purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public String getVerificatPerson() {
		return verificatPerson;
	}

	public void setVerificatPerson(String verificatPerson) {
		this.verificatPerson = verificatPerson;
	}

	public String getVerificatDate() {
		return verificatDate;
	}

	public void setVerificatDate(String verificatDate) {
		this.verificatDate = verificatDate;
	}

	public Integer getVerificatStatus() {
		return verificatStatus;
	}

	public void setVerificatStatus(Integer verificatStatus) {
		this.verificatStatus = verificatStatus;
	}

	public Float getSettlementQty() {
		return settlementQty;
	}

	public void setSettlementQty(Float settlementQty) {
		this.settlementQty = settlementQty;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getReceivedStatus() {
		return receivedStatus;
	}

	public void setReceivedStatus(Integer receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	public Float getActualDeliveryQty() {
		return actualDeliveryQty;
	}

	public void setActualDeliveryQty(Float actualDeliveryQty) {
		this.actualDeliveryQty = actualDeliveryQty;
	}

	public String getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}

	public String getPurchaseReason() {
		return purchaseReason;
	}

	public void setPurchaseReason(String purchaseReason) {
		this.purchaseReason = purchaseReason;
	}

	public String getFactoryMaterialCode() {
		return factoryMaterialCode;
	}

	public void setFactoryMaterialCode(String factoryMaterialCode) {
		this.factoryMaterialCode = factoryMaterialCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOriginalMaterial() {
		return originalMaterial;
	}

	public void setOriginalMaterial(String originalMaterial) {
		this.originalMaterial = originalMaterial;
	}

	public String getDrawingCode() {
		return drawingCode;
	}

	public void setDrawingCode(String drawingCode) {
		this.drawingCode = drawingCode;
	}

	public String getMaterialBomParamValueStr() {
		return materialBomParamValueStr;
	}

	public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
		this.materialBomParamValueStr = materialBomParamValueStr;
	}

	public String getMaterialSource() {
		return materialSource;
	}

	public void setMaterialSource(String materialSource) {
		this.materialSource = materialSource;
	}

	public String getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(String purchaseCode) {
		this.purchaseCode = purchaseCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
}

