package com.ecms.service;

import com.ecms.bean.DeliveryAddressInfo;
import com.ecms.dal.entity.DeliveryAddressEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.DeliveryAddressManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeliveryAddressService extends BaseService<DeliveryAddressEntity, DeliveryAddressInfo> {

	@Resource
	private DeliveryAddressManager manager;
	@Override
	protected BaseManager<DeliveryAddressEntity> getManager() {
		return manager;
	}

}
