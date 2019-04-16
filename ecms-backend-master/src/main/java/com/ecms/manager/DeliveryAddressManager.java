package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.DeliveryAddressEntity;
import com.ecms.dal.mapper.DeliveryAddressMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DeliveryAddressManager extends BaseManager<DeliveryAddressEntity> {

	@Resource
	private DeliveryAddressMapper mapper;
	@Override
	protected BasicMapper<DeliveryAddressEntity> getMapper() {
		return mapper;
	}

}
