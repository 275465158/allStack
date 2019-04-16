package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.DeliveryDetailEntity;
import com.ecms.dal.mapper.DeliveryDetailMapper;

@Component
public class DeliveryDetailManager extends BaseManager<DeliveryDetailEntity> {

	@Resource
	private DeliveryDetailMapper mapper;
	@Override
	protected BasicMapper<DeliveryDetailEntity> getMapper() {
		return mapper;
	}

}
