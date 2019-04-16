package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchaseOrderEntity;
import com.ecms.dal.mapper.PurchaseOrderMapper;

@Component
public class PurchaseOrderManager extends BaseManager<PurchaseOrderEntity> {

	@Resource
	private PurchaseOrderMapper mapper;
	@Override
	protected BasicMapper<PurchaseOrderEntity> getMapper() {
		return mapper;
	}

}
