package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchaseOrdersEntity;
import com.ecms.dal.mapper.PurchaseOrdersMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PurchaseOrdersManager extends BaseManager<PurchaseOrdersEntity> {

	@Resource
	private PurchaseOrdersMapper purchaseOrdersMapper;
	@Override
	protected BasicMapper<PurchaseOrdersEntity> getMapper() {
		return purchaseOrdersMapper;
	}
}
