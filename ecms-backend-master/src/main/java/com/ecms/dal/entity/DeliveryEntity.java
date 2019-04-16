package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="delivery")
public class DeliveryEntity extends BaseCompanyAndStatusEntity {

	@Column(name="delivery_code")
	private String deliveryCode;
	
	@Column(name="delivery_order_id")
	private String orderId;
	
	@Column(name="delivery_plan_date")
	private String planDate;
	
	@Column(name="delivery_prepared_by")
	private String preparedBy;
	
	@Column(name="delivery_status")
	private String deliveryStatus;

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getPreparedBy() {
		return preparedBy;
	}

	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
	
}
