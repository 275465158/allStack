package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name="warehousing_entry")
public class WarehousingEntryInfoEntity extends BaseCompanyAndStatusEntity {

	//物料id
	@Column(name="material_id")
	private Integer materialId;

	//仓库id
	@Column(name="repertory_id")
	private Integer repertoryId;

	//库位
	@Column(name="shelf_position")
	private String shelfPosition;

	//供应商id
	@Column(name="supplier_id")
	private Integer supplierId;

	//数量
	private Float qty;

	//批次
	@Column(name="materiel_batch")
	private String materielBatch;

	//采购原因
	@Column(name="purchase_cause")
	private String purchaseCause;

	//原图材料
	@Column(name="origin_of_materiel")
	private String originOfMateriel;

	//入库时间
	@Column(name="materiel_stock_from")
	private String materielStockFrom;

	//有效期
	@Column(name="materiel_expiry_date")
	private String materielExpiryDate;

	//签收人
	@Column(name="materiel_consignee")
	private String materielConsignee;

	//预计到货时间
	@Column(name="estimated_date")
	private String estimatedDate;

	//采购订单id
	@Column(name="purchase_order_id")
	private Integer purchaseOrderId;

	//采购价
	@Column(name="pruchase_price")
	private BigDecimal pruchasePrice;

	//入库状态 0待入库 1已入库
	@Column(name="inbound_status")
	private Integer inboundStatus;

	public BigDecimal getPruchasePrice() {
		return pruchasePrice;
	}

	public void setPruchasePrice(BigDecimal pruchasePrice) {
		this.pruchasePrice = pruchasePrice;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public String getShelfPosition() {
		return shelfPosition;
	}

	public void setShelfPosition(String shelfPosition) {
		this.shelfPosition = shelfPosition;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Float getQty() {
		return qty;
	}

	public void setQty(Float qty) {
		this.qty = qty;
	}

	public String getMaterielBatch() {
		return materielBatch;
	}

	public void setMaterielBatch(String materielBatch) {
		this.materielBatch = materielBatch;
	}

	public String getPurchaseCause() {
		return purchaseCause;
	}

	public void setPurchaseCause(String purchaseCause) {
		this.purchaseCause = purchaseCause;
	}

	public String getOriginOfMateriel() {
		return originOfMateriel;
	}

	public void setOriginOfMateriel(String originOfMateriel) {
		this.originOfMateriel = originOfMateriel;
	}

	public String getMaterielStockFrom() {
		return materielStockFrom;
	}

	public void setMaterielStockFrom(String materielStockFrom) {
		this.materielStockFrom = materielStockFrom;
	}

	public String getMaterielExpiryDate() {
		return materielExpiryDate;
	}

	public void setMaterielExpiryDate(String materielExpiryDate) {
		this.materielExpiryDate = materielExpiryDate;
	}

	public String getMaterielConsignee() {
		return materielConsignee;
	}

	public void setMaterielConsignee(String materielConsignee) {
		this.materielConsignee = materielConsignee;
	}

	public String getEstimatedDate() {
		return estimatedDate;
	}

	public void setEstimatedDate(String estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

	public Integer getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Integer purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public Integer getInboundStatus() {
		return inboundStatus;
	}

	public void setInboundStatus(Integer inboundStatus) {
		this.inboundStatus = inboundStatus;
	}
}
