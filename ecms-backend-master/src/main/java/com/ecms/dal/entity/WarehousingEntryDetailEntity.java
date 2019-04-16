package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="warehousing_entry_arrived_detail")
public class WarehousingEntryDetailEntity extends BaseCompanyAndStatusEntity {

	@Column(name="entry_id")
	private Integer entryId;
	
	@Column(name="warehousing_entry_arrived_detail_arrived_qty")
	private Float arrivedQty;
	
	@Column(name="warehousing_entry_arrived_detail_arrived_date")
	private String arrivedDate;
	
	@Column(name="warehousing_entry_arrived_detail_consignee")
	private String consignee;
	
	@Column(name="warehousing_entry_arrived_detail_status")
	private String detailStatus;
	
	@Column(name="warehousing_entry_arrived_detail_batch")
	private String arrivedBatch;
	
	@Column(name="warehousing_entry_arrived_detail_qualified_qty")
	private Float qualifiedQty;
	
	@Column(name="warehousing_entry_arrived_detail_unqualified_qty")
	private Float unqualifiedQty;
	
	@Column(name="warehousing_entry_arrived_detail_qualified_rate")
	private Float qualifiedRate;
	
	@Column(name="warehousing_entry_arrived_detail_inspector")
	private String inspector;
	
	@Column(name="warehousing_entry_arrived_detail_inspect_date")
	private String inspectDate;
	
	@Column(name="warehousing_entry_arrived_detail_inspect_status")
	private String inspectStatus;

	@Column(name="warehousing_entry_arrived_detail_entry_qty")
	private Float entryQty;
	
	@Column(name="warehousing_entry_arrived_detail_entry_date")
	private String entryDate;
	
	@Column(name="warehousing_entry_arrived_detail_entry_status")
	private String entryStatus;
	
	@Column(name="warehousing_entry_arrived_detail_expiry_date")
	private String expiryDate;
	
	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public Float getArrivedQty() {
		return arrivedQty;
	}

	public void setArrivedQty(Float arrivedQty) {
		this.arrivedQty = arrivedQty;
	}

	public String getArrivedDate() {
		return arrivedDate;
	}

	public void setArrivedDate(String arrivedDate) {
		this.arrivedDate = arrivedDate;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getDetailStatus() {
		return detailStatus;
	}

	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}

	public String getArrivedBatch() {
		return arrivedBatch;
	}

	public void setArrivedBatch(String arrivedBatch) {
		this.arrivedBatch = arrivedBatch;
	}

	public Float getQualifiedQty() {
		return qualifiedQty;
	}

	public void setQualifiedQty(Float qualifiedQty) {
		this.qualifiedQty = qualifiedQty;
	}

	public Float getUnqualifiedQty() {
		return unqualifiedQty;
	}

	public void setUnqualifiedQty(Float unqualifiedQty) {
		this.unqualifiedQty = unqualifiedQty;
	}

	public Float getQualifiedRate() {
		return qualifiedRate;
	}

	public void setQualifiedRate(Float qualifiedRate) {
		this.qualifiedRate = qualifiedRate;
	}

	public String getInspector() {
		return inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	public String getInspectDate() {
		return inspectDate;
	}

	public void setInspectDate(String inspectDate) {
		this.inspectDate = inspectDate;
	}

	public String getInspectStatus() {
		return inspectStatus;
	}

	public void setInspectStatus(String inspectStatus) {
		this.inspectStatus = inspectStatus;
	}

	public Float getEntryQty() {
		return entryQty;
	}

	public void setEntryQty(Float entryQty) {
		this.entryQty = entryQty;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryStatus() {
		return entryStatus;
	}

	public void setEntryStatus(String entryStatus) {
		this.entryStatus = entryStatus;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
}
