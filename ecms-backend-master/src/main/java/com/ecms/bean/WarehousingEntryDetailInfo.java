package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 入库单明细
 */
public class WarehousingEntryDetailInfo extends BaseCompanyAndStatusInfo {

	//明细id
	private Integer entryId;
	
	//入库单实体
	private WarehousingEntryInfo entryInfo;
	
	//有效期
	private String expiryDate;
	
	//到货数量
	private Float arrivedQty;
	
	//到货时间
	private String arrivedDate;

	//入库数量
	private Float entryQty;
	
	//入库时间
	private String entryDate;
	
	//入库状态
	private String entryStatus;
	
	//签收人
	private String consignee;
	
	//状态
	private String detailStatus;
	
	//到货批次
	private String arrivedBatch;
	
	//合格数量
	private Float qualifiedQty;
	
	//不合格数量
	private Float unqualifiedQty;
	
	//合格率
	private Float qualifiedRate;
	
	//检验人
	private String inspector;
	
	//检验时间
	private String inspectDate;
	
	//检验状态
	private String inspectStatus;

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public WarehousingEntryInfo getEntryInfo() {
		return entryInfo;
	}

	public void setEntryInfo(WarehousingEntryInfo entryInfo) {
		this.entryInfo = entryInfo;
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
