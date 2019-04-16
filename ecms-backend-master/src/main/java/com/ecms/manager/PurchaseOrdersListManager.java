package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchaseOrdersEntity;
import com.ecms.dal.entity.PurchaseOrdersListEntity;
import com.ecms.dal.mapper.PurchaseOrdersListMapper;
import com.ecms.dal.mapper.PurchaseOrdersMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PurchaseOrdersListManager extends BaseManager<PurchaseOrdersListEntity> {

	@Resource
	private PurchaseOrdersListMapper purchaseOrdersListMapper;
	@Override
	protected BasicMapper<PurchaseOrdersListEntity> getMapper() {
		return purchaseOrdersListMapper;
	}
}
