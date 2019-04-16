package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="z_purchase_order")
public class PurchaseOrderEntity extends BaseCompanyAndStatusEntity {
	
	@Column(name="purchase_order_material_id")
	private Integer materialId;
	
	@Column(name="purchase_order_supplier_id")
	private Integer supplierId;
	
	@Column(name="purchase_order_qty")
	private Float qty;
	
	@Column(name="purchase_order_reason")
	private String reason;
	
	@Column(name="purchase_order_lastest_date")
	private String lastestTime;
	
	@Column(name="purchase_order_price")
	private Float price;
	
	@Column(name="purchase_order_total")
	private Float total;
	
	@Column(name="purchase_order_buyer")
	private String buyer;
	
	@Column(name="purchase_order_number")
	private String orderNumber;
	
	@Column(name="purchase_order_approval")
	private Boolean approval;
	
	@Column(name="purchase_order_date")
	private String orderDate;
	
	@Column(name="purchase_order_payment_date")
	private String paymentDate;
	
	@Column(name="purchase_order_arrived")
	private Boolean arrived;
	
	@Column(name="purchase_order_arrived_date")
	private String arrivedDate;
	
	@Column(name="purchase_order_repertory_id")
	private Integer repertoryId;
	
	@Column(name="purchase_order_estimated_date")
	private String estimatedDate;

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

	public String getLastestTime() {
		return lastestTime;
	}

	public void setLastestTime(String lastestTime) {
		this.lastestTime = lastestTime;
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

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public String getEstimatedDate() {
		return estimatedDate;
	}

	public void setEstimatedDate(String estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

	
}
