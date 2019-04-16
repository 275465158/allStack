package com.ecms.bean;

/***
 * 
 * @author alan.wu
 * 2018年8月1日
 * @Description 发货单
 */
public class DeliveryInfo extends BaseCompanyAndStatusInfo {

	//发货单号
	private String deliveryCode;
	
	//订单记录
	private String orderId;
	
	//计划发货日期
	private String planDate;
	
	//制表人
	private String preparedBy;
	
	//发货状态
	private String deliveryStatus;
	
	//发货总数
	private Float totalQty;

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

	public Float getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Float totalQty) {
		this.totalQty = totalQty;
	}
	
	
}
