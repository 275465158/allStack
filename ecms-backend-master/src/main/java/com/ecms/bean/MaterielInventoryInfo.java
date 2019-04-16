package com.ecms.bean;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 产品库存记录实体
 */
public class MaterielInventoryInfo extends BaseCompanyAndStatusInfo {
	
	//产品id
	private Integer materielId;
	
	//产品实体
	private MaterielInfo materiel;
	
	//原图材料
	private String originalMateriel;
	
	
	//库存位置
	private String shelfPosition;
	
	//供应商id
	private Integer supplierId;
	
	//供应商实体
	private SupplierInfo supplier;

	//采购批次
	private String materielBatch;

	//采购原因
	private String purchaseCause;

	//原料来源
	private String originOfMateriel;

	//入库时间
	private String stockFrom;

	//保质期
	private String expiryDate;

	//签收人
	private String consignee;
	
	//入库单明细id
	private Integer entryId;
	
	//入库单明细实体
	private WarehousingEntryDetailInfo entryDetail;
	
	private Float qty;

	private String supplierName;

	private List<Map<String,Object>> tableDataRow;

	private Integer materielTableId;

	public List<Map<String, Object>> getTableDataRow() {
		return tableDataRow;
	}

	public void setTableDataRow(List<Map<String, Object>> tableDataRow) {
		this.tableDataRow = tableDataRow;
	}

	public Integer getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Integer materielId) {
		this.materielId = materielId;
	}

	public MaterielInfo getMateriel() {
		return materiel;
	}

	public void setMateriel(MaterielInfo materiel) {
		this.materiel = materiel;
	}

	public String getOriginalMateriel() {
		return originalMateriel;
	}

	public void setOriginalMateriel(String originalMateriel) {
		this.originalMateriel = originalMateriel;
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

	public SupplierInfo getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierInfo supplier) {
		this.supplier = supplier;
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

	public String getStockFrom() {
		return stockFrom;
	}

	public void setStockFrom(String stockFrom) {
		this.stockFrom = stockFrom;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public Float getQty() {
		return qty;
	}

	public void setQty(Float qty) {
		this.qty = qty;
	}

	public WarehousingEntryDetailInfo getEntryDetail() {
		return entryDetail;
	}

	public void setEntryDetail(WarehousingEntryDetailInfo entryDetail) {
		this.entryDetail = entryDetail;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getMaterielTableId() {
		return materielTableId;
	}

	public void setMaterielTableId(Integer materielTableId) {
		this.materielTableId = materielTableId;
	}
}
