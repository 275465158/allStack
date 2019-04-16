package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="purchase_orders_stay_detail")
public class PurchaseOrdersStayDetailEntity extends BaseCompanyAndStatusEntity {
    //待采购记录id
    @Column(name ="purchase_orders_stay_id")
    private Integer orderStayId;

    //采购记录id
    @Column(name = "purchase_orders_id")
    private Integer orderId;

    //采购记录明细id
    @Column(name="purchase_orders_list_id")
    private Integer orderListId;

    //采购数量
    @Column(name = "purchase_ordered_number")
    private Float orderedNumber;

    //待采购状态
    @Column(name = "purchase_orders_stay_detail_status")
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

    public Integer getOrderListId() {
        return orderListId;
    }

    public void setOrderListId(Integer orderListId) {
        this.orderListId = orderListId;
    }
}
