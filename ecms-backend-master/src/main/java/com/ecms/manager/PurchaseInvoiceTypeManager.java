package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchaseInvoiceTypeEntity;
import com.ecms.dal.mapper.PurchaseInvoiceTypeMapper;

@Component
public class PurchaseInvoiceTypeManager extends BaseManager<PurchaseInvoiceTypeEntity> {

	@Resource
	private PurchaseInvoiceTypeMapper typeMapper;

	@Override
	protected BasicMapper<PurchaseInvoiceTypeEntity> getMapper() {
		return typeMapper;
	}

}
