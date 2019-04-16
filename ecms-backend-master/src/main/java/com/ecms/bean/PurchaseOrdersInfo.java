package com.ecms.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhengli
 * 2018年8月1日
 * @Description 采购订单实体
 */
public class PurchaseOrdersInfo extends BaseCompanyAndStatusInfo {

	//物料id
	private Integer materialId;

	//采购编号
	private String purchaseCode;

	//待入库数量（合格数）
	private Float stayInboundQty;

	//入库状态 0：待收货，1：待检测，2：待入库，3：已入库
	private Integer inboundStatus;

	//待收货数量（实际采购数量）
	private Float stayPurQty;

	//待采购id
	private Integer purchaseOrdersStayId;

	//不合格数
	private Float unqualifiedQty;

	//仓库id
	private Integer repertoryId;

	//仓库名称
	private String repertoryName;

	//供应商id
	private Integer supplierId;

	//采购单价（下单实际价格）
	private BigDecimal purchasePrice;

	//采购总价（实际采购数量*下单时的采购单价）
	private BigDecimal purchasePriceTotal;

	//到货时间（下单时间+预计到货天数）
	private String arriveDate;

	//物料编码
	private String materialCode;

	//物料名称
	private String materialName;

	//待采购实体
	private PurchaseOrdersStayInfo purchaseOrdersStayInfo;

	//页面传过来的预计到货天数
	private Double purchaseDays;

	//采购人
	private String purchasePerson;

	//采购原因
	private String purchaseReason;

	//创建时间（采购时间）
	private String purchaseDate;

	//参数
	private String paramValue;

	//原图材料
	private String originalMaterial;

	//供应商名称
	private String supplierName;

	//当前登陆账号人employeeId
	private Integer employeeId;

	//采购数量
	private Float purchaseQty;

	//已收货数量
	private Float receivedQty;

	//未收货数量
	private Float unReceivedQty;

	//结算方式
	private Integer paymentMethod;

	//采购单明细list
	List<List<Map<String,String>>> purchaseOrdersListInfoList;

	//核销状态
	private Integer verificatStatus;

	//0:根据待采购生成的  1:手工创建的
	private Integer flag;

	//待采购单下单明细
	List<Map<String,String>> mapList;

	//列表编号、每个公司都从1开始
	private Integer purchaseNumber;

	//供应商联系人
	private String supplierContact;

	//供应商联系电话号码
	private String supplierNumber;

	//供应商联系手机号码
	private String supplierPhone;

	//供应商联系传真号码
	private String supplierFaxNumber;

	//供应商联系邮箱
	private String supplierEmail;

	//采购电话号码
	private String purchasePhoneNumber;

	//采购手机号码
	private String purchasePhone;

	//采购传真号码
	private String purchaseFaxNumber;

	//采购电子邮箱
	private String purchaseEmail;

	//是否含税 1：含税  0：不含税
	private Integer ifTax;

	//是否含运费 1：含运费  0：不含运费
	private Integer ifFreight;

	//运费
	private BigDecimal freight;

	//备注
	private String remark;

	//采购协议
	private String purchaseAgreement;

	//收货地址id
	private Integer deliveryAddressId;

	//收货地址实体
	private DeliveryAddressInfo deliveryAddressInfo;

	//是否自提 1：自提  2：不自提
	private Integer ifPickUp;

	//产品总价
	private BigDecimal productPriceTotal;

	//采购单总价
	private BigDecimal subtotal;

	//采购单审批状态
	//add date 2019/4/1
	//author alan
	private Integer reviewStatus;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public String getPurchaseCode() { return purchaseCode; }

	public void setPurchaseCode(String purchaseCode) { this.purchaseCode = purchaseCode; }

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

	public PurchaseOrdersStayInfo getPurchaseOrdersStayInfo() {
		return purchaseOrdersStayInfo;
	}

	public void setPurchaseOrdersStayInfo(PurchaseOrdersStayInfo purchaseOrdersStayInfo) {
		this.purchaseOrdersStayInfo = purchaseOrdersStayInfo;
	}

	public Double getPurchaseDays() {
		return purchaseDays;
	}

	public void setPurchaseDays(Double purchaseDays) {
		this.purchaseDays = purchaseDays;
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

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getOriginalMaterial() {
		return originalMaterial;
	}

	public void setOriginalMaterial(String originalMaterial) {
		this.originalMaterial = originalMaterial;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public List<List<Map<String, String>>> getPurchaseOrdersListInfoList() {
		return purchaseOrdersListInfoList;
	}

	public void setPurchaseOrdersListInfoList(List<List<Map<String, String>>> purchaseOrdersListInfoList) {
		this.purchaseOrdersListInfoList = purchaseOrdersListInfoList;
	}

	public Integer getVerificatStatus() {
		return verificatStatus;
	}

	public void setVerificatStatus(Integer verificatStatus) {
		this.verificatStatus = verificatStatus;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public List<Map<String, String>> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map<String, String>> mapList) {
		this.mapList = mapList;
	}

	public Integer getPurchaseNumber() {
		return purchaseNumber;
	}

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

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public String getRepertoryName() {
		return repertoryName;
	}

	public void setRepertoryName(String repertoryName) {
		this.repertoryName = repertoryName;
	}

	public DeliveryAddressInfo getDeliveryAddressInfo() {
		return deliveryAddressInfo;
	}

	public void setDeliveryAddressInfo(DeliveryAddressInfo deliveryAddressInfo) {
		this.deliveryAddressInfo = deliveryAddressInfo;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
}

