package com.ecms.bean;

import java.math.BigDecimal;

/**
 * 
 * @author zhengli
 * 2018年12月18日
 * @Description 采购订单明细实体
 */
public class PurchaseOrdersListInfo extends BaseCompanyAndStatusInfo {

	//物料id
	private Integer materialId;

	//采购单id
	private Integer purchaseOrdersId;

	//采购单号
	private String purchaseCode;

	//物料编码
	private String materialCode;

	//物料名称
	private String materialName;

	//已收货数量
	private Float receivedQty;

	//未收货数量
	private Float unReceivedQty;

	//待收货数量（实际采购数量）
	private Float stayPurQty;

	//不合格数
	private Float unqualifiedQty;

	//采购单价（下单实际价格）
	private BigDecimal purchasePrice;

	//明细总价
	private BigDecimal purchasePriceTotal;

	//采购数量
	private Float purchaseQty;

	//核算人
	private String accountPerson;

	//核算时间
	private String accountDate;

	//结算方式
	private Integer query;

	//标识
	private Boolean flag;

	//供应商名称
	private String supplierName;

	//供应商id
	private Integer supplierId;

	//采购原因
	private String purchaseReason;

	//物料bom
	private MaterialBomInfo materialBomInfo;

	//核销人
	private String verificatPerson;

	//核销时间
	private String verificatDate;

	//核销状态
	private Integer verificatStatus;

	//采购时间
	private String purchaseDate;

	//采购人
    private String purchasePerson;

	//物料类型
	private String type;

	//结算数量
	private Float settlementQty;

	//供应商中维护的重量
	private Double weight;

	//仓库id
	private Integer repertoryId;

	//实际收货数
	private Float actualDeliveryQty;

	//收货状态 0:未收货 1:部分收货 2:已收货
	private Integer receivedStatus;

	//预计到货时间
	private String arriveDate;

	/**工厂物料编码**/
	private String factoryMaterialCode;

	/**物料类型**/
	private String number;

	/**原图材料**/
	private String originalMaterial;

	/**图号**/
	private String drawingCode;

	/**参数**/
	private String materialBomParamValueStr;

	/**物料来源**/
	private String materialSource;

	//单位
	private String unit;

	//供应商物料id
	private Integer supplierMaterialId;

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

	public String getAccountPerson() {
		return accountPerson;
	}

	public void setAccountPerson(String accountPerson) {
		this.accountPerson = accountPerson;
	}

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public Integer getQuery() {
		return query;
	}

	public void setQuery(Integer query) {
		this.query = query;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getPurchaseCode() {
		return purchaseCode;
	}

	public void setPurchaseCode(String purchaseCode) {
		this.purchaseCode = purchaseCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPurchaseReason() {
		return purchaseReason;
	}

	public void setPurchaseReason(String purchaseReason) {
		this.purchaseReason = purchaseReason;
	}

	public MaterialBomInfo getMaterialBomInfo() {
		return materialBomInfo;
	}

	public void setMaterialBomInfo(MaterialBomInfo materialBomInfo) {
		this.materialBomInfo = materialBomInfo;
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

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

    public String getPurchasePerson() {
        return purchasePerson;
    }

    public void setPurchasePerson(String purchasePerson) {
        this.purchasePerson = purchasePerson;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Float getSettlementQty() {
		return settlementQty;
	}

	public void setSettlementQty(Float settlementQty) {
		this.settlementQty = settlementQty;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public Float getActualDeliveryQty() {
		return actualDeliveryQty;
	}

	public void setActualDeliveryQty(Float actualDeliveryQty) {
		this.actualDeliveryQty = actualDeliveryQty;
	}

	public Integer getReceivedStatus() {
		return receivedStatus;
	}

	public void setReceivedStatus(Integer receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	public String getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getSupplierMaterialId() {
		return supplierMaterialId;
	}

	public void setSupplierMaterialId(Integer supplierMaterialId) {
		this.supplierMaterialId = supplierMaterialId;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
}
