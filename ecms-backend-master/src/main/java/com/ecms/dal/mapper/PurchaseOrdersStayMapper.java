package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchaseOrdersStayEntity;
import org.apache.ibatis.annotations.Update;

public interface  PurchaseOrdersStayMapper extends BasicMapper<PurchaseOrdersStayEntity> {

    @Update("UPDATE purchase_orders_stay SET status=0 WHERE order_id=#{orderId}")
    void updateStayPurchaseStatusByOrderId(Integer orderId);
}
