package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchaseShippingMethodEntity;
import com.ecms.dal.mapper.PurchaseShippingMethodMapper;

@Component
public class PurchaseShippingMethodManager extends BaseManager<PurchaseShippingMethodEntity> {

	@Resource
	private PurchaseShippingMethodMapper methodMapper;

	@Override
	protected BasicMapper<PurchaseShippingMethodEntity> getMapper() {
		return methodMapper;
	}

}
