package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchasePaymentPeriodEntity;
import com.ecms.dal.mapper.PurchasePaymentPeriodMapper;

@Component
public class PurchasePaymentPeriodManager extends BaseManager<PurchasePaymentPeriodEntity> {

	@Resource
	private PurchasePaymentPeriodMapper periodMapper;

	@Override
	protected BasicMapper<PurchasePaymentPeriodEntity> getMapper() {
		return periodMapper;
	}

}
