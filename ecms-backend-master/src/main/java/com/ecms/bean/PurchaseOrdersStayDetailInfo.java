package com.ecms.bean;

public class PurchaseOrdersStayDetailInfo extends BaseCompanyAndStatusInfo {
    private Integer orderStayId;
    private Integer orderId;
    private Integer orderListId;
    private Float orderedNumber;
    private String detailStatus;

    public Integer getOrderStayId() {
        return orderStayId;
    }

    public void setOrderStayId(Integer orderStayId) {
        this.orderStayId = orderStayId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderListId() {
        return orderListId;
    }

    public void setOrderListId(Integer orderListId) {
        this.orderListId = orderListId;
    }

    public Float getOrderedNumber() {
        return orderedNumber;
    }

    public void setOrderedNumber(Float orderedNumber) {
        this.orderedNumber = orderedNumber;
    }

    public String getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(String detailStatus) {
        this.detailStatus = detailStatus;
    }
}
