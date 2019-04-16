package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchaseQueueEntity;
import com.ecms.dal.mapper.PurchaseQueueMapper;

@Component
public class PurchaseQueueManager extends BaseManager<PurchaseQueueEntity> {

	@Resource
	private PurchaseQueueMapper mapper;
	@Override
	protected BasicMapper<PurchaseQueueEntity> getMapper() {
		return mapper;
	}

}
