package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name="purchase_orders")
public class PurchaseOrdersEntity extends BaseCompanyAndStatusEntity {

	//物料id
	@Column(name="material_id")
	private Integer materialId;

	//采购编号
	@Column(name="purchase_code")
	private String purchaseCode;

	//待入库数量（合格数）
	@Column(name="stay_inbound_qty")
	private Float stayInboundQty;

	//入库状态 0：待收货，1：待检测，2：待入库，3：已入库
	@Column(name="inbound_status")
	private Integer inboundStatus;

	//采购单id
	@Column(name="purchase_orders_stay_id")
	private Integer purchaseOrdersStayId;

	//实际采购数量
	@Column(name="stay_pur_qty")
	private Float stayPurQty;

	//不合格数
	@Column(name="unqualified_qty")
	private Float unqualifiedQty;

	//仓库id
	@Column(name="repertory_id")
	private Integer repertoryId;

	//供应商id
	@Column(name="supplier_id")
	private Integer supplierId;

	//采购单价（下单实际价格）
	@Column(name="purchase_price")
	private BigDecimal purchasePrice;

	//采购总价（实际采购数量*下单时的采购单价）
	@Column(name="purchase_price_total")
	private BigDecimal purchasePriceTotal;

	//到货时间（下单时间+预计到货天数）
	@Column(name="arrive_date")
	private String arriveDate;

	/**物料编码**/
	@Column(name="material_code")
	private String materialCode;

	/**物料名称**/
	@Column(name="material_name")
	private String materialName;

	//采购人
	@Column(name="purchase_person")
	private String purchasePerson;

	//采购原因
	@Column(name="purchase_reason")
	private String purchaseReason;

	//采购时间
	@Column(name="purchase_date")
	private String purchaseDate;

	//采购数量
	@Column(name="purchase_qty")
	private Float purchaseQty;

	//已收货数量
	@Column(name="received_qty")
	private Float receivedQty;

	//未收货数量
	@Column(name = "un_received_qty")
	private Float unReceivedQty;

	//0:根据待采购生成的  1:手工创建的
	@Column(name="flag")
	private Integer flag;

	//列表编号、每个公司都从1开始
	@Column(name="purchase_number")
	private Integer purchaseNumber;

	//供应商联系人
	@Column(name="supplier_contact")
	private String supplierContact;

	//供应商联系电话号码
	@Column(name="supplier_number")
	private String supplierNumber;

	//供应商联系手机号码
	@Column(name="supplier_phone")
	private String supplierPhone;

	//供应商联系传真号码
	@Column(name="supplier_fax_number")
	private String supplierFaxNumber;

	//供应商联系邮箱
	@Column(name="supplier_email")
	private String supplierEmail;

	//采购电话号码
	@Column(name="purchase_phone_umber")
	private String purchasePhoneNumber;

	//采购手机号码
	@Column(name="purchase_phone")
	private String purchasePhone;

	//采购传真号码
	@Column(name="purchase_fax_number")
	private String purchaseFaxNumber;

	//采购电子邮箱
	@Column(name="purchase_email")
	private String purchaseEmail;

	//是否含税 1：含税  0：不含税
	@Column(name="if_tax")
	private Integer ifTax;

	//是否含运费 1：含运费  0：不含运费
	@Column(name="if_freight")
	private Integer ifFreight;

	//运费
	@Column(name="freight")
	private BigDecimal freight;

	//备注
	@Column(name="remark")
	private String remark;

	//采购协议
	@Column(name="purchase_agreement")
	private String purchaseAgreement;

	//收货地址id
	@Column(name="delivery_address_id")
	private Integer deliveryAddressId;

	//是否自提 1：自提  2：不自提
	@Column(name="if_pick_up")
	private Integer ifPickUp;

	//产品总价
	@Column(name="product_price_total")
	private BigDecimal productPriceTotal;

	//审批状态
	@Column(name="purchase_orders_review_status")
	private Integer reviewStatus;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public String getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(String purchaseCode) {
		this.purchaseCode = purchaseCode;
	}

	public Float getStayInboundQty() {
		return stayInboundQty;
	}

	public void setStayInboundQty(Float stayInboundQty) {
		this.stayInboundQty = stayInboundQty;
	}

	public Integer getInboundStatus() {
		return inboundStatus;
	}

	public void setInboundStatus(Integer inboundStatus) {
		this.inboundStatus = inboundStatus;
	}

	public Integer getPurchaseOrdersStayId() {
		return purchaseOrdersStayId;
	}

	public void setPurchaseOrdersStayId(Integer purchaseOrdersStayId) {
		this.purchaseOrdersStayId = purchaseOrdersStayId;
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

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
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

	public String getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
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

	public String getPurchasePerson() {
		return purchasePerson;
	}

	public void setPurchasePerson(String purchasePerson) {
		this.purchasePerson = purchasePerson;
	}

	public String getPurchaseReason() {
		return purchaseReason;
	}

	public void setPurchaseReason(String purchaseReason) {
		this.purchaseReason = purchaseReason;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Float getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(Float purchaseQty) {
		this.purchaseQty = purchaseQty;
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getPurchaseNumber() { return purchaseNumber; }

	public void setPurchaseNumber(Integer purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}

	public String getSupplierContact() {
		return supplierContact;
	}

	public void setSupplierContact(String supplierContact) {
		this.supplierContact = supplierContact;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}

	public String getSupplierFaxNumber() {
		return supplierFaxNumber;
	}

	public void setSupplierFaxNumber(String supplierFaxNumber) {
		this.supplierFaxNumber = supplierFaxNumber;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public String getPurchasePhoneNumber() {
		return purchasePhoneNumber;
	}

	public void setPurchasePhoneNumber(String purchasePhoneNumber) {
		this.purchasePhoneNumber = purchasePhoneNumber;
	}

	public String getPurchasePhone() {
		return purchasePhone;
	}

	public void setPurchasePhone(String purchasePhone) {
		this.purchasePhone = purchasePhone;
	}

	public String getPurchaseFaxNumber() {
		return purchaseFaxNumber;
	}

	public void setPurchaseFaxNumber(String purchaseFaxNumber) {
		this.purchaseFaxNumber = purchaseFaxNumber;
	}

	public String getPurchaseEmail() {
		return purchaseEmail;
	}

	public void setPurchaseEmail(String purchaseEmail) {
		this.purchaseEmail = purchaseEmail;
	}

	public Integer getIfTax() {
		return ifTax;
	}

	public void setIfTax(Integer ifTax) {
		this.ifTax = ifTax;
	}

	public Integer getIfFreight() {
		return ifFreight;
	}

	public void setIfFreight(Integer ifFreight) {
		this.ifFreight = ifFreight;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPurchaseAgreement() {
		return purchaseAgreement;
	}

	public void setPurchaseAgreement(String purchaseAgreement) {
		this.purchaseAgreement = purchaseAgreement;
	}

	public Integer getDeliveryAddressId() {
		return deliveryAddressId;
	}

	public void setDeliveryAddressId(Integer deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}

	public Integer getIfPickUp() {
		return ifPickUp;
	}

	public void setIfPickUp(Integer ifPickUp) {
		this.ifPickUp = ifPickUp;
	}

	public BigDecimal getProductPriceTotal() {
		return productPriceTotal;
	}

	public void setProductPriceTotal(BigDecimal productPriceTotal) {
		this.productPriceTotal = productPriceTotal;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
}
