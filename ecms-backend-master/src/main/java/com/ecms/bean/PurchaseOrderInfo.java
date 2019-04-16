package com.ecms.bean;

import java.util.List;

/**
 * 
 * @author alan.wu 2018年8月2日
 * @Description 采购订单实体
 */
public class PurchaseOrderInfo extends BaseCompanyAndStatusInfo {

	// 物料id
	private Integer materialId;

	// 物料实体
	private MaterialBomInfo materialBom;

	// 供应商id
	private Integer supplierId;

	// 供应商实体
	private SupplierInfo supplier;

	// 所在仓库id
	private Integer repertoryId;

	// 仓库实体
	private RepertoryInfo repertory;

	// 订单数量
	private Float qty;

	// 价格
	private Float price;

	// 总计
	private Float total;

	// 原因
	private String reason;

	// 预计到货时间
	private String lastestTime;

	// 采购人
	private String buyer;

	// 订单号
	private String orderNumber;

	// 审批结果
	private Boolean approval;

	// 下单时间
	private String orderDate;

	// 付款时间
	private String paymentDate;

	// 是否到货
	private Boolean arrived;

	// 到货时间
	private String arrivedDate;

	// 预计到货时间
	private String estimatedDate;
	// 采购列表
	private List<PurchaseQueueInfo> queueList;
	

	public List<PurchaseQueueInfo> getQueueList() {
		return queueList;
	}

	public void setQueueList(List<PurchaseQueueInfo> queueList) {
		this.queueList = queueList;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public String getLastestTime() {
		return lastestTime;
	}

	public void setLastestTime(String lastestTime) {
		this.lastestTime = lastestTime;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Boolean getApproval() {
		return approval;
	}

	public void setApproval(Boolean approval) {
		this.approval = approval;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Boolean getArrived() {
		return arrived;
	}

	public void setArrived(Boolean arrived) {
		this.arrived = arrived;
	}

	public String getArrivedDate() {
		return arrivedDate;
	}

	public void setArrivedDate(String arrivedDate) {
		this.arrivedDate = arrivedDate;
	}

	public MaterialBomInfo getMaterialBom() {
		return materialBom;
	}

	public void setMaterialBom(MaterialBomInfo materialBom) {
		this.materialBom = materialBom;
	}

	public SupplierInfo getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierInfo supplier) {
		this.supplier = supplier;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public RepertoryInfo getRepertory() {
		return repertory;
	}

	public void setRepertory(RepertoryInfo repertory) {
		this.repertory = repertory;
	}

	public String getEstimatedDate() {
		return estimatedDate;
	}

	public void setEstimatedDate(String estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

}
