package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.DeliveryEntity;
import com.ecms.dal.mapper.DeliveryMapper;

@Component
public class DeliveryManager extends BaseManager<DeliveryEntity> {

	@Resource
	private DeliveryMapper mapper;
	@Override
	protected BasicMapper<DeliveryEntity> getMapper() {
		return mapper;
	}

}
