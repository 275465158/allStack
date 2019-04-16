package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchaseOrdersStayEntity;
import com.ecms.dal.mapper.PurchaseOrdersStayMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PurchaseOrdersStayManager extends BaseManager<PurchaseOrdersStayEntity> {

	@Resource
	private PurchaseOrdersStayMapper purchaseOrdersStayMapper;
	@Override
	protected BasicMapper<PurchaseOrdersStayEntity> getMapper() {
		return purchaseOrdersStayMapper;
	}

	public void updateStayPurchaseStatusByOrderId(Integer orderId){
		purchaseOrdersStayMapper.updateStayPurchaseStatusByOrderId(orderId);
	}
}
