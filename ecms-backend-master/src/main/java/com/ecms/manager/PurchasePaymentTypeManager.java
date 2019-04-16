package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchasePaymentTypeEntity;
import com.ecms.dal.mapper.PurchasePaymentTypeMapper;

@Component
public class PurchasePaymentTypeManager extends BaseManager<PurchasePaymentTypeEntity> {

	@Resource
	private PurchasePaymentTypeMapper paymentMapper;

	@Override
	protected BasicMapper<PurchasePaymentTypeEntity> getMapper() {
		return paymentMapper;
	}

}
